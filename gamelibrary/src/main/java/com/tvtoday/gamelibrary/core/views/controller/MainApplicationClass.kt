package com.tvtoday.crosswordhindi.views.controller

import android.app.Application

class MainApplicationClass :Application() {


    companion object{
        var application: MainApplicationClass? = null

        fun getInstance(): MainApplicationClass? {
            return application
        }
    }


   override fun onCreate() {
       // ActivityLifecycleCallback.register(this);
        super.onCreate()


    }

}