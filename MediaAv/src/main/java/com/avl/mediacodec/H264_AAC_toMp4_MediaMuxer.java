package com.avl.mediacodec;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;

import java.io.IOException;
import java.nio.ByteBuffer;

public class H264_AAC_toMp4_MediaMuxer {

    private static String TAG = "H264_AAC_toMp4_MediaMuxer";

    public static void muxerAudio(String sdcard_path) {

        try {
            MediaMuxer audioMuxer;
            MediaExtractor mediaExtractor = null;
            audioMuxer = new MediaMuxer(sdcard_path + "/mux_audio.mp4", MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            mediaExtractor.setDataSource(sdcard_path + "input.mp4");

            int trackCount = mediaExtractor.getTrackCount();

            int audioTrackIndex = -1;

            for (int i = 0; i < trackCount; i++) {
                MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                String mineType = trackFormat.getString(MediaFormat.KEY_MIME);

                if (mineType.startsWith("audio/")) {
                    audioTrackIndex = i;
                }
            }

            ByteBuffer byteBuffer = ByteBuffer.allocate(500 * 1024);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();

            mediaExtractor.selectTrack(audioTrackIndex);
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(audioTrackIndex);
            int writeAudioIndex = audioMuxer.addTrack(trackFormat);
            audioMuxer.start();

            long sampletime = 0;
            long first_sampletime = 0;
            long second_sampletime = 0;

            {
                mediaExtractor.readSampleData(byteBuffer, 0);
                first_sampletime = mediaExtractor.getSampleTime();
                mediaExtractor.advance();
                second_sampletime = mediaExtractor.getSampleFlags();
                sampletime = Math.abs(second_sampletime - first_sampletime);
            }

            mediaExtractor.unselectTrack(audioTrackIndex);
            mediaExtractor.selectTrack(audioTrackIndex);

            while (true) {
                int readSampleCount = mediaExtractor.readSampleData(byteBuffer, 0);
                if (readSampleCount < 0) {
                    break;
                }

                bufferInfo.size = readSampleCount;
                bufferInfo.offset = 0;
                bufferInfo.flags = mediaExtractor.getSampleFlags();
                bufferInfo.presentationTimeUs += sampletime;

                audioMuxer.writeSampleData(writeAudioIndex, byteBuffer, bufferInfo);
                byteBuffer.clear();
                mediaExtractor.advance();
            }

            audioMuxer.stop();
            audioMuxer.release();
            mediaExtractor.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * h264 复合成 mp4
     * @param sdcard_path
     */
    public static void muxerVideo(String sdcard_path)
    {
        try {
            MediaMuxer videoMuxer;
            MediaExtractor mediaExtractor;
            mediaExtractor = new MediaExtractor();
            videoMuxer = new MediaMuxer(sdcard_path+"/mux_video.mp4",MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);

            int trackCount = mediaExtractor.getTrackCount();
            int videoTrackIndex = -1;
            for (int i=0;i<trackCount;i++)
            {
                MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                String mineType = trackFormat.getString(MediaFormat.KEY_MIME);
                if(mineType.startsWith("video/"))
                {
                    videoTrackIndex = i;
                }
            }

            ByteBuffer byteBuffer = ByteBuffer.allocate(500*1024);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();

            mediaExtractor.selectTrack(videoTrackIndex);
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(videoTrackIndex);
            int writeVideoIndex = videoMuxer.addTrack(trackFormat);

            videoMuxer.start();
            long sampletime =0;
            long first_sampletime = 0;
            long second_sampletime =0;

            {
                mediaExtractor.readSampleData(byteBuffer,0);
                first_sampletime = mediaExtractor.getSampleTime();
                mediaExtractor.advance();

                second_sampletime = mediaExtractor.getSampleTime();
                sampletime = Math.abs(second_sampletime-first_sampletime);
            }

            mediaExtractor.unselectTrack(videoTrackIndex);
            mediaExtractor.selectTrack(videoTrackIndex);

            while(true)
            {
                int readSampleCount = mediaExtractor.readSampleData(byteBuffer,0);
                if(readSampleCount <0)
                {
                    break;
                }

                bufferInfo.size = readSampleCount;
                bufferInfo.offset = 0;
                bufferInfo.flags = mediaExtractor.getSampleFlags();
                bufferInfo.presentationTimeUs +=sampletime;
                videoMuxer.writeSampleData(writeVideoIndex,byteBuffer,bufferInfo);
                byteBuffer.clear();
                mediaExtractor.advance();
            }

            videoMuxer.stop();
            videoMuxer.release();
            mediaExtractor.release();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 合并上面生成的 aac （mp4容器） 和 h264 (mp4容器)
     */

    public static void combineVideo(String sdcard_path)
    {
        MediaMuxer mediaMuxer;
        MediaExtractor videoExtractor = null;
        MediaExtractor audioExtractor = null;

        try
        {
            audioExtractor = new MediaExtractor();
            videoExtractor = new MediaExtractor();

            mediaMuxer = new MediaMuxer(sdcard_path+"/mux_output.mp4",MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            videoExtractor.setDataSource(sdcard_path+"/mux_video.mp4");
            audioExtractor.setDataSource(sdcard_path+"/mux_audio.mp4");

            int trackCount = videoExtractor.getTrackCount();
            int videoTrackIndex = -1;

            for (int i=0;i<trackCount;i++)
            {
                MediaFormat videoFormat = videoExtractor.getTrackFormat(i);
                String mineType = videoFormat.getString(MediaFormat.KEY_MIME);
                if(mineType.startsWith("video/"))
                {
                    videoTrackIndex =i;
                }
            }

            int audioTrackIndex = -1;
            for (int i=0;i<trackCount;i++)
            {
                MediaFormat audioFormat = audioExtractor.getTrackFormat(i);
                String mineType = audioFormat.getString(MediaFormat.KEY_MIME);
                if(mineType.startsWith("audio/"))
                {
                    audioTrackIndex = i;
                }
            }


            ByteBuffer byteBuffer = ByteBuffer.allocate(500*1024);
            MediaCodec.BufferInfo audiobufferInfo = new MediaCodec.BufferInfo();
            MediaCodec.BufferInfo videobufferInfo = new MediaCodec.BufferInfo();

            videoExtractor.selectTrack(videoTrackIndex);
            audioExtractor.selectTrack(audioTrackIndex);

            MediaFormat videotrackFormat = videoExtractor.getTrackFormat(videoTrackIndex);
            int writeVideoIndex = mediaMuxer.addTrack(videotrackFormat);

            MediaFormat audiotrackFormat = audioExtractor.getTrackFormat(audioTrackIndex);
            int writeAudioIndex = mediaMuxer.addTrack(audiotrackFormat);

            mediaMuxer.start();

            //video
            long sampletime = 0;
            long first_sampletime =0;
            long second_sampletime = 0;
            {
                videoExtractor.readSampleData(byteBuffer,0);
                first_sampletime = videoExtractor.getSampleTime();
                videoExtractor.advance();
                second_sampletime = videoExtractor.getSampleTime();
                sampletime = Math.abs(second_sampletime- first_sampletime);
            }


            videoExtractor.unselectTrack(videoTrackIndex);
            videoExtractor.selectTrack(videoTrackIndex);

            while(true)
            {
                int readSampleCount = videoExtractor.readSampleData(byteBuffer,0);

                if(readSampleCount < 0) break;

                audiobufferInfo.size = readSampleCount;
                audiobufferInfo.offset = 0;
                audiobufferInfo.flags = videoExtractor.getSampleFlags();
                audiobufferInfo.presentationTimeUs +=sampletime;
                mediaMuxer.writeSampleData(writeVideoIndex,byteBuffer,audiobufferInfo);
                byteBuffer.clear();
                videoExtractor.advance();
            }

            //audio
            while(true)
            {
                int readSampleCount = audioExtractor.readSampleData(byteBuffer,0);
                if(readSampleCount<0) break;
                videobufferInfo.size = readSampleCount;
                videobufferInfo.offset = 0;
                videobufferInfo.flags = audioExtractor.getSampleFlags();
                videobufferInfo.presentationTimeUs +=sampletime;
                mediaMuxer.writeSampleData(writeAudioIndex,byteBuffer,videobufferInfo);
                byteBuffer.clear();
                audioExtractor.advance();
            }

            mediaMuxer.start();
            mediaMuxer.release();

            audioExtractor.release();
            videoExtractor.release();

            } catch (IOException e) {
                e.printStackTrace();
            }


    }

}
