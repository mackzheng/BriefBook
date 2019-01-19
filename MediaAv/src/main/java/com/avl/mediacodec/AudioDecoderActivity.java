package com.avl.mediacodec;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.avl.mediaav.R;

import java.io.IOException;

public class AudioDecoderActivity extends Activity
{
    protected static AudioDecoderThread mAudioDecoder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_decoder);

        if (savedInstanceState == null)
        {
            int tag = getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment(), "tag").commit();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mAudioDecoder.stop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String SAMPLE = Environment.getExternalStorageDirectory() + "/temp.aac";

        public PlaceholderFragment() {
            mAudioDecoder = new AudioDecoderThread();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_audio_decoder, container, false);

            final Button btn = (Button) rootView.findViewById(R.id.play);
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        mAudioDecoder.startPlay(SAMPLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return rootView;
        }
    }
}