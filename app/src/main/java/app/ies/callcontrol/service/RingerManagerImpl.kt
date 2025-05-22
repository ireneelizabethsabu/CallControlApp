package app.ies.callcontrol.service

import android.content.Context
import android.media.AudioManager

class RingerManagerImpl {
    fun muteRinger(context: Context){
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    fun unMuteRinger(context: Context){
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    }
}