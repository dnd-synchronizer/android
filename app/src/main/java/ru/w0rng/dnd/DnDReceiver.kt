package ru.w0rng.dnd

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.media.AudioManager
import androidx.core.content.ContextCompat.getSystemService


class DnDReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        println("DnDReceiver onReceive")
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val ringerMode = audioManager.ringerMode

        val mNotificationManager = getSystemService(context, NotificationManager::class.java)


        if (mNotificationManager?.currentInterruptionFilter == 2) {
            println("DnD mode is on")
            RestApiClient().chengeStatus(true)
        } else {
            println("DnD mode is off")
            RestApiClient().chengeStatus(false)
        }
    }
}