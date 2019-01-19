package com.avl.mediacodec;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;

public class AudioDecoderThread {

    private static final int TIMEOUT_US = 1000;
    private MediaExtractor mediaExtractor;
    private MediaCodec mediaCodec;

    private boolean eosReceived;
    private int mSampleRate = 0;

    public void startPlay(String path) throws IOException {
        eosReceived = false;

        mediaExtractor = new MediaExtractor();

        try {
            mediaExtractor.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }


        int channel = 0;
        for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
            MediaFormat format = mediaExtractor.getTrackFormat(i);
            String mime = format.getString(MediaFormat.KEY_MIME);

            if (mime.startsWith("audio/")) {
                mediaExtractor.selectTrack(i);

                ByteBuffer csd = format.getByteBuffer("csd-0");

                for (int k = 0; k < csd.capacity(); ++k) {
                }

                mSampleRate = format.getInteger(MediaFormat.KEY_SAMPLE_RATE);
                channel = format.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
            }
        }

        MediaFormat format = makeaacCodecSpecificData(MediaCodecInfo.CodecProfileLevel.AACObjectLC, mSampleRate, channel);

        if (format == null) return;

        mediaCodec = MediaCodec.createDecoderByType("audio/mp4a-latm");
        mediaCodec.configure(format, null, null, 0);

        if (mediaCodec == null) return;

        mediaCodec.start();

        new Thread(AACDecoderAndPlayRunnable).start();


    }

    private MediaFormat makeaacCodecSpecificData(int audioProfile, int sampleRate, int channelConfig) {
        MediaFormat format = new MediaFormat();
        format.setString(MediaFormat.KEY_MIME, "audio/mp4a-latm");
        format.setInteger(MediaFormat.KEY_SAMPLE_RATE, sampleRate);
        format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, channelConfig);

        int samplingFreq[] = {96000, 88200, 64000, 48000, 44100, 32000, 24000, 22050, 1600, 1200, 11025, 8000};

        int sampleIndex = -1;
        for (int i = 0; i < samplingFreq.length; i++) {
            if (samplingFreq[i] == sampleIndex) {
                sampleIndex = i;
            }
        }

        if (sampleIndex == -1) {
            return null;
        }

        ByteBuffer csd = ByteBuffer.allocate(2);

        csd.put((byte) ((audioProfile << 3) | (sampleIndex >> 1)));

        csd.position(1);
        csd.put((byte) ((byte) ((sampleIndex << 7) & 0x80) | (channelConfig << 3)));
        csd.flip();
        format.setByteBuffer("csd-0", csd);
        for (int k = 0; k < csd.capacity(); ++k) {
            Log.e("AudioDecoderThread", "csd:" + csd.array()[k]);
        }
        return format;
    }

    Runnable AACDecoderAndPlayRunnable = new Runnable() {
        @Override
        public void run() {
            AACDecoderAndPlay();
        }
    };

    public void AACDecoderAndPlay() {
        ByteBuffer[] inputBuffers = mediaCodec.getInputBuffers();
        ByteBuffer[] outputBuffers = mediaCodec.getOutputBuffers();

        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();

        int buffsize = AudioTrack.getMinBufferSize(mSampleRate, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT);

        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, mSampleRate, AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT, buffsize, AudioTrack.MODE_STREAM);

        audioTrack.play();

        while (!eosReceived) {
            int inIndex = mediaCodec.dequeueInputBuffer(TIMEOUT_US);
            if (inIndex >= 0) {
                ByteBuffer buffer = inputBuffers[inIndex];
                int sampleSize = mediaExtractor.readSampleData(buffer, 0);
                if (sampleSize < 0) {
                    mediaCodec.queueInputBuffer(inIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                } else {
                    mediaCodec.queueInputBuffer(inIndex, 0, sampleSize, mediaExtractor.getSampleTime(), 0);
                    mediaExtractor.advance();
                }

                int outIndex = mediaCodec.dequeueOutputBuffer(info, TIMEOUT_US);
                switch (outIndex) {
                    case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
                        outputBuffers = mediaCodec.getOutputBuffers();
                        break;
                    case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
                        MediaFormat format = mediaCodec.getOutputFormat();
                        audioTrack.setPlaybackRate(format.getInteger(MediaFormat.KEY_SAMPLE_RATE));
                        break;
                    case MediaCodec.INFO_TRY_AGAIN_LATER:
                        break;
                    default:
                        ByteBuffer outBuffer = outputBuffers[outIndex];
                        final byte[] chunk = new byte[info.size];
                        outBuffer.get(chunk);
                        outBuffer.clear();

                        audioTrack.write(chunk, info.offset, info.offset + info.size);
                        mediaCodec.releaseOutputBuffer(outIndex, false);
                        break;
                }

                if ((info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                    break;
                }
            }
        }

        mediaCodec.stop();
        mediaCodec.release();
        mediaCodec = null;

        mediaExtractor.release();
        mediaExtractor = null;

        audioTrack.stop();
        audioTrack.release();
        audioTrack = null;
    }


    public void stop() {
        eosReceived = true;
    }
}
