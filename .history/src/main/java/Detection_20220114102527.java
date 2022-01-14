import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

public class Detection extends JPanel {

    private BufferedImage mImg;

    /**
     * 开始检测运动轨迹并取帧保留
     */
    public void running() {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//          原始图像
            Mat beforeImg = new Mat();
//          处理后图像
            Mat afterImg = new Mat();
//          捕获帧
            VideoCapture capture = new VideoCapture(0);
            int height = (int) capture.get(Videoio.CAP_PROP_FRAME_HEIGHT);
            int width = (int) capture.get(Videoio.CAP_PROP_FRAME_WIDTH);
            if (height == 0 || width == 0) {
                throw new Exception("未检测到相机!");
            }
            JFrame frame = new JFrame("检测窗口");
            Detection panel = new Detection();
            frame.setContentPane(panel);
            frame.setResizable(false);
            frame.setVisible(true);
            frame.setSize(width + frame.getInsets().left + frame.getInsets().right,
                    height + frame.getInsets().top + frame.getInsets().bottom);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//          背景分割器
            BackgroundSubtractorMOG2 bs = Video.createBackgroundSubtractorMOG2();
            while (frame.isShowing()) {
                capture.read(beforeImg);
//                帧率
                bs.setHistory(50);
//                学习率
                bs.apply(beforeImg, afterImg, 0.1f);
//                向量
                List<MatOfPoint> contours = new ArrayList<>();
                Mat hierarchy = new Mat();
                Imgproc.findContours(afterImg, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
                Imgproc.dilate(afterImg, afterImg, new Mat(), new Point(-1, -1), 2);

                for (MatOfPoint mf : contours) {
//                    像素面积
                    if (Imgproc.contourArea(mf) < 100) {
                        continue;
                    }
//                     Imgproc.drawContours(beforeImg, contours,
//                     contours.indexOf(mf), new Scalar(0, 255, 255));
                    Imgproc.fillConvexPoly(beforeImg, mf, new Scalar(0, 255, 0));
                    Rect r = Imgproc.boundingRect(mf);
                    Imgproc.rectangle(beforeImg, r.tl(), r.br(), new Scalar(0, 0, 255), 2);
//                    视频帧输出路径
                    Imgcodecs.imwrite("E:\\work\\" + System.currentTimeMillis() + ".jpg", beforeImg);
                }
                panel.mImg = panel.matToBufferedImage(beforeImg);
                beforeImg.release();
                panel.repaint();
            }
        } catch (Exception e) {
            System.out.println("--异常--" + e);
            System.exit(-1);
        } finally {
            System.out.println("--结束--");
        }
    }

    /**
     *  mat转BufferedImage工具
     * @param mat
     * @return
     */
        private BufferedImage matToBufferedImage(Mat mat) {
        if (mat.height() > 0 && mat.width() > 0) {
            BufferedImage image = new BufferedImage(mat.width(), mat.height(), BufferedImage.TYPE_3BYTE_BGR);
            WritableRaster raster = image.getRaster();
            DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
            byte[] data = dataBuffer.getData();
            mat.get(0, 0, data);
            return image;
        }
        return null;
    }

    /**
     * 组件渲染
     * @param g
     */
    public void paintComponent(Graphics g) {
        if (mImg != null) {
            g.drawImage(mImg, 0, 0, mImg.getWidth(), mImg.getHeight(), this);
        }
    }
}


