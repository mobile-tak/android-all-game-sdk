package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabd_word_search.Word
import java.lang.reflect.Modifier

class ShabdjalUtils {

    companion object{
        fun saveGame(context: Context, crossWordBoardItem:Set<Word>, date:String){
            val gson = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT) // STATIC|TRANSIENT in the default configuration
                .create()
            val logs = gson.toJson(crossWordBoardItem)
            PrefDataShabdjal.setStringPrefs(context, date, logs)
        }

        fun getGame(gameData: String): Set<Word> {
            val myType = object : TypeToken<Set<Word>>() {}.type
            val gson = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT) // STATIC|TRANSIENT in the default configuration
                .create()
            return gson.fromJson(gameData, myType)
        }
    }
}