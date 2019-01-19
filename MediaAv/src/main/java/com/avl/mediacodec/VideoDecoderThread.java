package com.avl.mediacodec;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;
import android.view.Surface;

import java.io.IOException;
import java.nio.ByteBuffer;

public class VideoDecoderThread extends Thread {

    private static final String VIDEO = "video/";
    private static final String TAG = "VideoDecoder";

    private MediaExtractor mediaExtractor;
    private MediaCodec mediaCodec;
    private boolean eosReceived;

    //初始化解码器，并启动解码器
    public boolean init(Surface surface, String filePath) {
        eosReceived = false;
        try {
            //把音频和视频的数据进行分离
            mediaExtractor = new MediaExtractor();
            mediaExtractor.setDataSource(filePath);

            for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
                MediaFormat format = mediaExtractor.getTrackFormat(i);

                String mime = format.getString(MediaFormat.KEY_MIME);//获取流的类型

                if (mime.startsWith(VIDEO)) {
                    mediaExtractor.selectTrack(i); //选中视频流
                    mediaCodec = MediaCodec.createDecoderByType(mime); //通过类型，创建解码器
                    mediaCodec.configure(format, surface, null, 0); //配置解码器
                    mediaCodec.start(); //启动解码器
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();

        ByteBuffer[] inputBuffers = mediaCodec.getInputBuffers(); //获取输入流buffer
        mediaCodec.getOutputBuffers();

        boolean isInput = true;
        boolean first = false;
        long startWhen = 0;

        while (!eosReceived) {
            if (isInput) {
                int inputIndex = mediaCodec.dequeueInputBuffer(10000);

                if (inputIndex >= 0) {
                    ByteBuffer inputBuffer = inputBuffers[inputIndex];
                    int sampleSize = mediaExtractor.readSampleData(inputBuffer, 0);
                    if (mediaExtractor.advance() && sampleSize > 0) {
                        mediaCodec.queueInputBuffer(inputIndex, 0, sampleSize, mediaExtractor.getSampleTime(), 0);
                    } else {
                        mediaCodec.queueInputBuffer(inputIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                        isInput = false;
                    }
                }
            }

            int outIndex = mediaCodec.dequeueOutputBuffer(info, 10000);

            switch (outIndex) {
                case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
                    break;
                case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
                    break;
                case MediaCodec.INFO_TRY_AGAIN_LATER:
                    break;
                default:
                    if (!first) {
                        startWhen = System.currentTimeMillis();
                        first = true;
                    }

                    try {
                        long sleepTime = (info.presentationTimeUs / 1000) - (System.currentTimeMillis() - startWhen);

                        if (sleepTime > 0) {

                            Thread.sleep(sleepTime);

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mediaCodec.releaseOutputBuffer(outIndex, true);
                    break;
            }

            if ((info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                Log.d(TAG, "OutPutBuffer BuFFER_FLAG_END_OF_STREAM");
                break;
            }
        }

        mediaCodec.stop();
        mediaCodec.release();
        mediaExtractor.release();
    }

    public void close() {
        eosReceived = true;
    }

}
