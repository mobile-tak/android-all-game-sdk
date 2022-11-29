package com.tvtoday.gamelibrary.shabdamgamesdk.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.tvtoday.gamelibrary.R;
import com.tvtoday.gamelibrary.shabdamgamesdk.GameActivity;
import com.tvtoday.gamelibrary.shabdamgamesdk.GameDataManager;
import com.tvtoday.gamelibrary.shabdamgamesdk.event.CleverTapEvent;
import com.tvtoday.gamelibrary.shabdamgamesdk.event.CleverTapEventConstants;
import com.tvtoday.gamelibrary.shabdamgamesdk.pref.CommonPreference;
import com.tvtoday.gamelibrary.shabdamgamesdk.ui.englishShabdam.englishGameActivity.EnglishGameActivity;
import com.tvtoday.gamelibrary.shabdamgamesdk.utils.ShabdamLanguagePreference;

public class ShabdamPaheliActivity extends ShabdamBaseActivity {

    private Button aage_bade_btn;
    private CheckBox checkBox;
    private ImageView ivRuleHome,iv_LangChange;
    private RelativeLayout rLayBgImage;

    private LinearLayout llHindi;
    private LinearLayout llEng;
    private TextView tvEng;
    private TextView tvHindi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_shabdam_paheli);

            //initialisation view---------------------------
            aage_bade_btn = findViewById(R.id.aage_bade_btn);
            checkBox = findViewById(R.id.check_box_btn);

            rLayBgImage = findViewById(R.id.rLayBgImage);
            ivRuleHome = findViewById(R.id.ivRuleHome);


            ivRuleHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }catch (Exception ex){

        }


        if(!TextUtils.isEmpty(CommonPreference.getInstance(ShabdamPaheliActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE)) && CommonPreference.getInstance(ShabdamPaheliActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).equals("hindi")){

        }else{
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                rLayBgImage.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.englis_rule) );
            } else {
                rLayBgImage.setBackground(ContextCompat.getDrawable(this, R.drawable.englis_rule));
            }
        }


        aage_bade_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonPreference.getInstance(ShabdamPaheliActivity.this.getApplicationContext()).getBoolean(CommonPreference.Key.IS_RULE_SHOWN) == true) {
                    CleverTapEvent.getCleverTapEvents(ShabdamPaheliActivity.this).createOnlyEvent(CleverTapEventConstants.GO_FORWARD);
                    CleverTapEvent.getCleverTapEvents(ShabdamPaheliActivity.this).createOnlyEvent(CleverTapEventConstants.DONOTSHOW);
                } else {
                    CleverTapEvent.getCleverTapEvents(ShabdamPaheliActivity.this).createOnlyEvent(CleverTapEventConstants.GO_FORWARD);
                }

                if(!TextUtils.isEmpty(CommonPreference.getInstance(ShabdamPaheliActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE)) && CommonPreference.getInstance(ShabdamPaheliActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).equals("hindi")){
                    startActivity(new Intent(ShabdamPaheliActivity.this, GameActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(ShabdamPaheliActivity.this, EnglishGameActivity.class));
                    finish();
                }
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                CommonPreference.getInstance(ShabdamPaheliActivity.this.getApplicationContext()).put(CommonPreference.Key.IS_RULE_SHOWN, buttonView.isChecked());
            }
        });

        tvHindi = findViewById(R.id.tvHindi);
        tvEng = findViewById(R.id.tvEng);
        llHindi = findViewById(R.id.llHindi);
        llEng = findViewById(R.id.llEng);

        if(CommonPreference.getInstance(ShabdamPaheliActivity.this).getString(CommonPreference.Key.SHABDAM_LANGUAGE)!=null &&
                CommonPreference.getInstance(ShabdamPaheliActivity.this).getString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).equals("hindi")){

            llHindi.setBackgroundColor(Color.parseColor("#4267B2"));

            //llHindi.setBackgroundColor(Color.BLACK);
            llEng.setBackgroundColor(Color.WHITE);

            tvEng.setTextColor(Color.parseColor("#000000"));
            tvHindi.setTextColor(Color.parseColor("#FFFFFF"));
        }else{
            llEng.setBackgroundColor(Color.parseColor("#4267B2"));

            //llEng.setBackgroundColor(Color.BLACK);
            llHindi.setBackgroundColor(Color.WHITE);

            tvHindi.setTextColor(Color.parseColor("#000000"));
            tvEng.setTextColor(Color.parseColor("#FFFFFF"));
        }

        llEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CleverTapEvent.getCleverTapEvents(ShabdamPaheliActivity.this).createOnlyEvent(CleverTapEventConstants.BUTTON_ENGLISH);

                if(CommonPreference.getInstance(ShabdamPaheliActivity.this).getString(CommonPreference.Key.SHABDAM_LANGUAGE)!=null &&
                        CommonPreference.getInstance(ShabdamPaheliActivity.this).getString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).equals("english")) {
                    llEng.setClickable(false);
                }else{

                    try{
                        GameDataManager.getInstance().getDataList().clear();

                        llEng.setBackgroundColor(Color.parseColor("#4267B2"));

                        // llEng.setBackgroundColor(Color.BLACK);
                        llHindi.setBackgroundColor(Color.WHITE);

                        tvHindi.setTextColor(Color.parseColor("#000000"));
                        tvEng.setTextColor(Color.parseColor("#FFFFFF"));


                        ShabdamLanguagePreference.getInstance(ShabdamPaheliActivity.this).setLanguage("");
                        CommonPreference.getInstance(ShabdamPaheliActivity.this).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.ENGLISH);
                        CommonPreference.getInstance(ShabdamPaheliActivity.this).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.ENGLISH);

                        Intent intent = new Intent(ShabdamPaheliActivity.this, EnglishGameActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }catch (Exception ex){

                    }


                }
            }
        });

        llHindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CleverTapEvent.getCleverTapEvents(ShabdamPaheliActivity.this).createOnlyEvent(CleverTapEventConstants.BUTTON_HINDI);

                if(CommonPreference.getInstance(ShabdamPaheliActivity.this).getString(CommonPreference.Key.SHABDAM_LANGUAGE)!=null &&
                        CommonPreference.getInstance(ShabdamPaheliActivity.this).getString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).equals("hindi")) {
                    llHindi.setClickable(false);
                }else{
                    try{
                        GameDataManager.getInstance().getDataList().clear();

                        llHindi.setBackgroundColor(Color.parseColor("#4267B2"));

                        // llHindi.setBackgroundColor(Color.BLACK);
                        llEng.setBackgroundColor(Color.WHITE);

                        tvEng.setTextColor(Color.parseColor("#000000"));
                        tvHindi.setTextColor(Color.parseColor("#FFFFFF"));

                        ShabdamLanguagePreference.getInstance(ShabdamPaheliActivity.this).setLanguage("hi");
                        CommonPreference.getInstance(ShabdamPaheliActivity.this).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.HINDI);
                        CommonPreference.getInstance(ShabdamPaheliActivity.this).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.HINDI);

                        Intent intent = new Intent(ShabdamPaheliActivity.this, GameActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }catch (Exception ex){

                    }


                }
            }
        });
    }

}