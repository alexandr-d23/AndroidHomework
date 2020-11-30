package com.alexd23.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.alexd23.example.myapplication.playlist.recycler.AudioAdapter
import kotlinx.android.synthetic.main.fragment_list.*

class PlayListFragment : Fragment() {

    private var startListener :StartListener? = null

    public interface StartListener{
        fun touched(audio: Audio)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        music_recycler.adapter = AudioAdapter(Audios.getAllAudio()){
            startListener?.touched(it)
        }
        music_recycler.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        startListener = context as? StartListener
    }


    companion object{
        fun newInstance(): PlayListFragment {
            val args = Bundle()
            val fragment = PlayListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}