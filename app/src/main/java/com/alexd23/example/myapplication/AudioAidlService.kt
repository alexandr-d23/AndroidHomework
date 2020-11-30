package com.alexd23.example.myapplication

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import java.util.*


class AudioAidlService : Service() {

    companion object{
        const val ACTION_START = "START"
        const val ACTION_STOP = "STOP"
        const val ACTION_NEXT = "NEXT"
        const val ACTION_PREVIOUS = "PREVIOUS"
    }

    private val NOTIFICATION_ID: Int = 9999
    private var callBack : IAidlCallback? = null
    private var mediaPlayer: MediaPlayer? = null
    private var currentMusic: Audio? = null
    private var localBinder: IBinderAudioAidl.Stub = object : IBinderAudioAidl.Stub(){

        override fun setCallBack(gotCallback: IAidlCallback?) {
            this@AudioAidlService.callBack = gotCallback
        }

        override fun startPlayAudio(audio: Audio) {
            this@AudioAidlService.startPlay(audio)
        }

        override fun pausePlayMusic(){
            this@AudioAidlService.pausePlay()
        }

        override fun continuePlayAudio(){
            this@AudioAidlService.continuePlay()
        }

        override fun nextPlayAudio(){
            this@AudioAidlService.nextPlay()
        }

        override fun previousPlayAudio(){
            this@AudioAidlService.previousPlay()
        }

        override fun rewindAudio(position: Int){
            this@AudioAidlService.mediaPlayer?.seekTo(position)
        }
    }



    override fun onCreate() {
        super.onCreate()
        startUpdatingSeekBar()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            ACTION_STOP -> pausePlay()
            ACTION_START -> continuePlay()
            ACTION_NEXT -> nextPlay()
            ACTION_PREVIOUS -> previousPlay()
        }
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(intent: Intent): IBinder {
        return localBinder
    }

    private fun startPlay(audio: Audio){
        currentMusic = audio
        mediaPlayer?.stop()
        mediaPlayer = MediaPlayer.create(this, audio.musicRes)
        mediaPlayer?.setOnCompletionListener {
            nextPlay()
        }
        mediaPlayer?.start()
        val notification = MyNotification.showNotification(this, audio, true)
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun startUpdatingSeekBar(){
        var timer = Timer().apply {
            scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    mediaPlayer?.let {
                        val position: Int = it.currentPosition / 1000 // In milliseconds
                        val duration: Int = it.duration / 1000 // In milliseconds
                        callBack?.updateSeekBar(duration, position)
                    }
                }
            }, 0, 1000)
        }
    }


    private fun continuePlay(){
        mediaPlayer?.start()
        currentMusic?.let {
            MyNotification.showNotification(this, it, true)
        }
    }

    private fun pausePlay(){
        mediaPlayer?.pause()
        currentMusic?.let {
            MyNotification.showNotification(this, it, false)
        }
    }
    private fun nextPlay(){
        currentMusic?.let {
            val audio = Audios.nextAudio(it.id)
            audio?.let { audioS->
                callBack?.setAudio(audioS)
                startPlay(audioS)
            }
        }
    }
    private fun previousPlay(){
        currentMusic?.let {
            val audio = Audios.previousAudio(it.id)
            audio?.let { audioS->
                callBack?.setAudio(audioS)
                startPlay(audioS)
            }
        }
    }


}