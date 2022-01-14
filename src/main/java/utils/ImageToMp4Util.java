//package utils;
//import org.bytedeco.ffmpeg.global.avcodec;
//import org.bytedeco.ffmpeg.global.avutil;
//import org.bytedeco.javacv.FFmpegFrameRecorder;
//import org.bytedeco.javacv.Java2DFrameConverter;
//
//import java.awt.image.BufferedImage;
//import java.text.Format;
//import java.text.SimpleDateFormat;
//import java.util.HashMap;
//
//public class ImageToMp4Util {
//    private HashMap<Integer,BufferedImage> imageMap;
//
//    public FFmpegFrameRecorder converToMp4(int width,int height){
//        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//        String mp4Path = "E:\\work\\" + format + ".mp4";
//        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(mp4Path,width, height);
//        try {
//            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
//            recorder.setFrameRate(25);
//            recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
//            recorder.setFormat("mp4");
//            recorder.start();
////            Java2DFrameConverter converter = new Java2DFrameConverter();
////            //录制一个22秒的视频
////            for (int i = 0; i < 22; i++) {
////                //一秒是25帧 所以要记录25次
////                for (int j = 0; j < 25; j++) {
////                    recorder.record(converter.getFrame(images.get(i)));
////                }
////            }
////            recorder.stop();
////            recorder.release();
//            return recorder;
//        } catch (Exception e) {
//            System.out.println("---转化失败---"+e);
//            e.printStackTrace();
//        } finally {
//            System.out.println("---转换结束---");
//        }
//        return null;
//    }
//}
