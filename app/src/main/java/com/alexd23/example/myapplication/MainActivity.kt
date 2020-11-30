package com.alexd23.example.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity(), PlayListFragment.StartListener,AudioInfoFragment.MediaPlayerController{

    private var secondFragment: AudioInfoFragment? = null

    private var binder: IBinderAudioAidl? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this,AudioAidlService::class.java))
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, PlayListFragment.newInstance())
            .addToBackStack("Recycler").commit()
    }

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = IBinderAudioAidl.Stub.asInterface(service).apply {
                setCallBack(object : IAidlCallback.Stub() {
                    @Throws
                    override fun setAudio(audio: Audio) {
                        runOnUiThread {
                            this@MainActivity.setAudio(audio)
                        }
                    }

                    @Throws
                    override fun updateSeekBar(duration: Int, position: Int) {
                        runOnUiThread {
                            this@MainActivity.updateSeekBar(duration,position)
                        }
                    }

                })
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
           binder = null
        }
    }

    override fun onBackPressed() {
        secondFragment = null
        val fm = supportFragmentManager
        if (fm.backStackEntryCount > 0)
            fm.popBackStack()
        else
            finish()
    }

    override fun onStart() {
        super.onStart()
        val myIntent = Intent(this, AudioAidlService::class.java)
        bindService(myIntent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        binder?.let{
            unbindService(mConnection)
            binder = null
        }
    }


    override fun touched(audio: Audio) {
        secondFragment = AudioInfoFragment.newInstance(audio)
        secondFragment?.let {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, it)
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun start(audio : Audio){
        binder?.startPlayAudio(audio)
    }


    override fun stop() {
        binder?.pausePlayMusic()
    }

    override fun next() {
        binder?.nextPlayAudio()
    }

    override fun previous() {
        binder?.previousPlayAudio()
    }

    override fun continuePlay() {
        binder?.continuePlayAudio()
    }

    override fun rewind(position: Int) {
        binder?.rewindAudio(position)
    }

    fun setAudio(audio: Audio) {
        secondFragment?.setAudio(audio)
    }

    fun updateSeekBar(duration:Int, position:Int) {
        secondFragment?.updateSeekBar(duration,position)
    }


}