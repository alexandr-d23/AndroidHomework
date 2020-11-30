package com.alexd23.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_information.*

class AudioInfoFragment : Fragment() {

    private var controller: MediaPlayerController? = null

    interface MediaPlayerController{
        fun start(audio: Audio)
        fun stop()
        fun next()
        fun previous()
        fun continuePlay()
        fun rewind(position: Int)
    }

    private var currentAudio : Audio? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_information, container,false)
    }

    private fun getTimeString(time: Int):String{
        return if(time in 0..9) "0$time"
        else "$time"
    }

    fun updateSeekBar(duration:Int, position:Int){
        item_current_pos.text = "${getTimeString(position/60)}:${getTimeString(position%60)} "
        item_duration.text = "${getTimeString(duration/60)}:${getTimeString(duration%60)}"
        seekBar.max = duration
        seekBar.progress = position
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val audio = arguments?.getSerializable("audio") as Audio
        setAudio(audio)
        item_previous.setOnClickListener {
            controller?.previous()
        }

        item_next.setOnClickListener {
            controller?.next()
        }

        item_start.setOnClickListener {
            controller?.continuePlay()
            item_start.visibility = View.INVISIBLE
            item_stop.visibility = View.VISIBLE
        }

        item_stop.setOnClickListener {
            controller?.stop()
            item_stop.visibility = View.INVISIBLE
            item_start.visibility = View.VISIBLE
        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) controller?.rewind(progress*1000)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        }
        )

    }

    fun setAudio(audio: Audio){
        item_current_pos.text = "00:00"
        item_duration.text = "00:00"
        currentAudio = audio
        item_author.text = audio.author
        item_title.text = audio.name
        item_image.setImageResource(audio.icon)
        controller?.start(audio)
        item_start.visibility = View.INVISIBLE
        item_stop.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance(audio: Audio): AudioInfoFragment {
            val args = Bundle()
            args.putSerializable("audio", audio)
            val fragment = AudioInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        controller = context as? MediaPlayerController
    }

}