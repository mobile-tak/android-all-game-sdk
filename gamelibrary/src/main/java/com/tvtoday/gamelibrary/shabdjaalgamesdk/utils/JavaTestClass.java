package com.tvtoday.gamelibrary.shabdjaalgamesdk.utils;

import android.content.Intent;

public class JavaTestClass {

    void testMain(){
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }
}
