package com.avl.mediacodec;


import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MuxerHelper {


    public static void combineTwoVideos(String audioVideoPaht,long audioStartTime,String frameVideoPath,File combinedVideoOutFile)
    {
        MediaExtractor audioVideoExtractor = new MediaExtractor();
        int mainAudioExtractorTrackIndex = -1;//提供视频的音轨
        int mainAudioMuxerTrackIndex = -1; //合成后视频的音轨
        int mainAudioMaxInputSize = 0;  //获取音频的最大值

        MediaExtractor  frameVideoExtractor = new MediaExtractor();

        int frameExtractorTrackIndex = -1;
        int frameMuxerTrackIndex = -1;
        int framemaxInputSize = 0;
        int frameRate = 0;
        long frameDuration = 0;


        MediaMuxer muxer = null;

        try {
            muxer = new MediaMuxer(combinedVideoOutFile.getPath(),MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);

            audioVideoExtractor.setDataSource(audioVideoPaht);
            int audioTrackCount = audioVideoExtractor.getTrackCount();

            for (int i=0;i<audioTrackCount;i++)
            {
                MediaFormat format = audioVideoExtractor.getTrackFormat(i);
                String mimeType = format.getString(MediaFormat.KEY_MIME);
                if(mimeType.startsWith("audio/"))
                {
                    mainAudioExtractorTrackIndex = i;
                    mainAudioMuxerTrackIndex = muxer.addTrack(format);
                    mainAudioMaxInputSize = format.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
                }
            }

            frameVideoExtractor.setDataSource(frameVideoPath);
            int trackCount = frameVideoExtractor.getTrackCount();

            for (int i=0;i<trackCount;i++)
            {
                MediaFormat format = frameVideoExtractor.getTrackFormat(i);
                String mimeType = format.getString(MediaFormat.KEY_MIME);

                if(mimeType.startsWith("video/"))
                {
                    frameExtractorTrackIndex = i;
                    frameMuxerTrackIndex = muxer.addTrack(format);
                    framemaxInputSize = format.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
                    frameRate = format.getInteger(MediaFormat.KEY_FRAME_RATE);
                    frameDuration = format.getLong(MediaFormat.KEY_DURATION);
                }
            }

            muxer.start(); //开始合成


            audioVideoExtractor.selectTrack(mainAudioExtractorTrackIndex);
            MediaCodec.BufferInfo audioBufferInfo = new MediaCodec.BufferInfo();
            ByteBuffer audioByteBuffer = ByteBuffer.allocate(mainAudioMaxInputSize);

            while(true)
            {
                int readSampleSize = audioVideoExtractor.readSampleData(audioByteBuffer,0);
                if(readSampleSize < 0 )
                {
                    audioVideoExtractor.unselectTrack(mainAudioExtractorTrackIndex);
                    break;
                }

                long sampleTime = audioVideoExtractor.getSampleTime();

                if(sampleTime < audioStartTime)
                {
                    audioVideoExtractor.advance();
                    continue;
                }


                if(sampleTime>audioStartTime+frameDuration)
                {
                    break;
                }

                audioBufferInfo.size = readSampleSize;
                audioBufferInfo.offset = 0;
                audioBufferInfo.flags = audioVideoExtractor.getSampleFlags();
                audioBufferInfo.presentationTimeUs = sampleTime - audioStartTime;

                muxer.writeSampleData(mainAudioExtractorTrackIndex,audioByteBuffer,audioBufferInfo);
                audioVideoExtractor.advance();
            }

            frameVideoExtractor.selectTrack(frameExtractorTrackIndex);
            MediaCodec.BufferInfo videoBufferInfo = new MediaCodec.BufferInfo();
            ByteBuffer videoByteBuffer = ByteBuffer.allocate(framemaxInputSize);


            while(true)
            {
                int readSampleSize = frameVideoExtractor.readSampleData(videoByteBuffer,0);
                if(readSampleSize <0)
                {
                    frameVideoExtractor.unselectTrack(frameExtractorTrackIndex);
                    break;
                }

                videoBufferInfo.size  = readSampleSize;
                videoBufferInfo.offset = 0;
                videoBufferInfo.flags = frameVideoExtractor.getSampleFlags();
                videoBufferInfo.presentationTimeUs +=1000*1000/frameRate;

                muxer.writeSampleData(frameMuxerTrackIndex,videoByteBuffer,videoBufferInfo);
                frameVideoExtractor.advance();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            audioVideoExtractor.release();
            frameVideoExtractor.release();
            if(muxer!=null)
            {
                muxer.stop();
                muxer.release();
            }
        }
    }
}
