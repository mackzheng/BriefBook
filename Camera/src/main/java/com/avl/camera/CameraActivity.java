package com.avl.camera;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class CameraActivity extends AppCompatActivity {

    private AspectFrameLayout mAspectLayout;
    // 当前长宽比值
    private float mCurrentRatio;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initView();
    }

    private void initView() {

     }
}
