package ru.w0rng.dnd

import android.annotation.SuppressLint
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context

class BootBrodcastService : BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            val intent = Intent(p0, BackgroundService::class.java)
            p0?.startForegroundService(intent)
        }
    }
}