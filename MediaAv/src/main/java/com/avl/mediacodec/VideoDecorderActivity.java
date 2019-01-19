package com.avl.mediacodec;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class VideoDecorderActivity extends Activity implements SurfaceHolder.Callback{

    private static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/video.mp4";

    private VideoDecoderThread mVideoDecoder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SurfaceView surfaceView =  new SurfaceView(this);
        surfaceView.getHolder().addCallback(this);
        setContentView(surfaceView);

        mVideoDecoder = new VideoDecoderThread();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if(mVideoDecoder!=null)
        {
            if(mVideoDecoder.init(holder.getSurface(),FILE_PATH))
            {
                mVideoDecoder.start();
            }else
            {
                mVideoDecoder = null;
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mVideoDecoder !=null)
        {
            mVideoDecoder.close();
        }
    }
}
