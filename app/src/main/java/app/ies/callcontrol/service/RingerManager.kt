package app.ies.callcontrol.service

import android.content.Context
import android.media.AudioManager

interface RingerManager{
    suspend fun saveCurrentRingerState()
}

