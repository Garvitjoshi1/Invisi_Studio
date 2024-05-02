package com.example.data_visuals

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer

class AlarmReceiver : BroadcastReceiver() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onReceive(context: Context, intent: Intent) {
        mediaPlayer = MediaPlayer.create(context, R.raw.alarm) // Replace with your sound file
        mediaPlayer.start()
    }
}
