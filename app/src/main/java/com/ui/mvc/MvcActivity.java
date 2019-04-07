package com.ui.mvc;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.avl.briefbook.R;

public class MvcActivity extends Activity
{

    private Dialog dialog;
    private TextView topContributor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mvc_activity);
        topContributor = findViewById(R.id.top_contributor);


    }




}
