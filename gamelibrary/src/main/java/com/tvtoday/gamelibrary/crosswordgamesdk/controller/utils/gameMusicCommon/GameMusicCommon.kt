package com.tvtoday.gamelibrary.crosswordgamesdk.controller.utils.gameMusicCommon

import android.content.Context
import android.media.MediaPlayer
import com.tvtoday.gamelibrary.R

class GameMusicCommon {

    companion object {
        private var mediaPlayer : MediaPlayer? = null
        private var mediaPlayerError: MediaPlayer? = null
        private var mediaPlayerCorrectWord: MediaPlayer? = null
        private var mediaPlayerGameComplete: MediaPlayer? = null

        private val mainGameSound = R.raw.peaceful_garden_healing

    fun playMainGameSoundPlay(context: Context)  {

        mediaPlayer = MediaPlayer.create(context, mainGameSound)
        mediaPlayer?.start()
        /* mediaPlayerError = MediaPlayer.create(this, R.raw.for_error_music)
         mediaPlayerCorrectWord = MediaPlayer.create(this, R.raw.small_applause)
         mediaPlayerGameComplete = MediaPlayer.create(this, R.raw.game_complete)*/
    }

        fun stopMainGameSound(context: Context){
            mediaPlayer = MediaPlayer.create(context, mainGameSound)
            mediaPlayer?.pause()
        }


     fun playErrorSound(context: Context, file:Int)  {
         mediaPlayerError = MediaPlayer.create(context, file)
     }




    }
}