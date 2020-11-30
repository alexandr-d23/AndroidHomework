package com.alexd23.example.myapplication

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object MyNotification {

        private const val CHANNEL_ID: String = "My_MediaPlayer_Channel"
        private const val CHANNEL_NAME: String = "MyMediaPlayer"
        private const val NOTIFICATION_ID: Int = 9999
        private var manager: NotificationManagerCompat? = null
        private var channelCreated : Boolean = false

        fun createNotificationChannel(context: Context){
                if(channelCreated) return
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                                NotificationManager.IMPORTANCE_LOW).apply {
                                lightColor = Color.BLACK
                                enableLights(true)
                        }
                        val channelManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        channelManager.createNotificationChannel(channel)
                        channelCreated = true
                }
        }

        fun showNotification(context: Context, audio: Audio, isStart: Boolean):Notification{
                if(!channelCreated) createNotificationChannel(context)
                val notification = getNotification(context, isStart, audio)
                if(manager == null) manager = NotificationManagerCompat.from(context)
                manager?.notify(NOTIFICATION_ID, notification)
                return notification
        }

        fun getNotification(context: Context,isStart: Boolean, audio: Audio) : Notification {
                val intent = Intent(context, MainActivity::class.java)
                val pendingContent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                val pendingIntentStop = getPendingIntentWithAction(AudioAidlService.ACTION_STOP,context)
                val pendingIntentStart = getPendingIntentWithAction(AudioAidlService.ACTION_START,context)
                val pendingIntentPrevious = getPendingIntentWithAction(AudioAidlService.ACTION_PREVIOUS,context)
                val pendingIntentNext = getPendingIntentWithAction(AudioAidlService.ACTION_NEXT,context)
                return NotificationCompat.Builder(context, CHANNEL_ID).apply {
                        setStyle(androidx.media.app.NotificationCompat.MediaStyle())
                        addAction(R.drawable.ic_previous_24, "Previous", pendingIntentPrevious)
                        if (isStart) addAction(R.drawable.ic_stop_24, "Stop", pendingIntentStop)
                        else {
                                addAction(R.drawable.ic_start_24, "Start", pendingIntentStart)
                                setOngoing(false)
                        }
                        addAction(R.drawable.ic_next_24, "Next", pendingIntentNext)
                        setSmallIcon(R.drawable.ic_launcher_foreground)
                        setOnlyAlertOnce(true)
                        setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        setContentIntent(pendingContent)
                        setContentTitle(audio.name)
                        setContentText(audio.author)
                        setLargeIcon(BitmapFactory.decodeResource(context.resources, audio.icon))
                        priority = NotificationCompat.PRIORITY_LOW
                }.build()
        }


        private fun getPendingIntentWithAction(actionRes: String,context: Context) : PendingIntent{
                val intent = Intent(context, AudioAidlService::class.java).apply {
                        action = actionRes
                }
                return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
}