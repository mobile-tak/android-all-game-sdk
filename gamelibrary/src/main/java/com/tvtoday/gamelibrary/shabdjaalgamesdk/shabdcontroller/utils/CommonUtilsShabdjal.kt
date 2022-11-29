package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.tvtoday.gamelibrary.R

import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.VargPaheliStartGame.CorsswordBoradItem
import java.lang.reflect.Modifier

class CommonUtilsShabdjal {

    companion object {
        //intent action-----------------------------------------------
        fun performIntent(context: Activity, className: Class<*>) {
            val intent = Intent(context, className)
            context.run {
                startActivity(intent)
                overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
            }
        }

        fun performIntentActivityResult(
            context: Activity,
            className: Class<*>,
            intentConstant: Int
        ) {
            val intent = Intent(context, className)
            context.startActivityForResult(intent, intentConstant)
            context.overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
        }

        fun performIntentFinish(context: Activity, className: Class<*>) {
            val intent = Intent(context, className)
            context.startActivity(intent)
            context.overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
            context.finish()
        }

        fun performIntentWithBundle(context: Activity, className: Class<*>, bundle: Bundle?) {

            val intent = Intent(context, className)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
            context.overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
        }

        fun performIntentWithBundleFinish(context: Activity, className: Class<*>, bundle: Bundle?) {


            val intent = Intent(context, className)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
            context.overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
            context.finish()
        }

        fun performIntentActivityResultBundle(
            context: Activity,
            className: Class<*>,
            intentConstant: Int,
            bundle: Bundle?
        ) {
            try {
                val intent = Intent(context, className)
                if (bundle != null) {
                    intent.putExtras(bundle)
                }
                context.startActivityForResult(intent, intentConstant)
                context.overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun performIntentActivityResultBundleFinish(
            context: Activity,
            className: Class<*>,
            intentConstant: Int,
            bundle: Bundle?
        ) {
            try {
                val intent = Intent(context, className)
                if (bundle != null) {
                    intent.putExtras(bundle)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                context.startActivityForResult(intent, intentConstant)
                context.overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
                context.finish()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun performIntentFinishAffinity(context: Activity, className: Class<*>) {
            val intent = Intent(context, className)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            context.overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
            context.finishAffinity()
        }

        //backpress
        fun backPress(context: Activity) {
            context.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
            context.finish()
        }

        fun isGpsEnable(activity: Activity): Boolean {
            val mLocationManager =
                activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

        fun saveGame(context: Context, crossWordBoardItem:ArrayList<CorsswordBoradItem>, date:String){
            val gson = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT) // STATIC|TRANSIENT in the default configuration
                .create()
            val logs = gson.toJson(crossWordBoardItem)
            PrefDataShabdjal.setStringPrefs(context, date, logs)
        }

        fun getGame(gameData: String): ArrayList<CorsswordBoradItem> {
            val myType = object : TypeToken<ArrayList<CorsswordBoradItem>>() {}.type
            val gson = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT) // STATIC|TRANSIENT in the default configuration
                .create()
            return gson.fromJson(gameData, myType)
        }
    }

}