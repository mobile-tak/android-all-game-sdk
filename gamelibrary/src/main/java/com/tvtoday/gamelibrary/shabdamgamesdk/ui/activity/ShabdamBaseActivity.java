package com.tvtoday.gamelibrary.shabdamgamesdk.ui.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tvtoday.gamelibrary.shabdamgamesdk.utils.ShabdamLanguagePreference;
import com.tvtoday.gamelibrary.shabdamgamesdk.utils.ShabdamLocaleHelper;


public abstract class ShabdamBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShabdamLanguagePreference.getInstance(ShabdamBaseActivity.this);
        ShabdamLocaleHelper.setLocale(this, ShabdamLanguagePreference.getInstance(ShabdamBaseActivity.this).getLanguage());
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(ShabdamLocaleHelper.onAttach(base));
    }


}
