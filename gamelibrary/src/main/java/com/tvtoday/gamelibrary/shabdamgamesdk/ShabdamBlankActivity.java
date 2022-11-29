package com.tvtoday.gamelibrary.shabdamgamesdk;

import android.os.Bundle;

import com.tvtoday.gamelibrary.R;
import com.tvtoday.gamelibrary.shabdamgamesdk.ui.activity.ShabdamBaseActivity;


public class ShabdamBlankActivity extends ShabdamBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shabdam_blank);
    }
}