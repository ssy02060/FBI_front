package com.example.fbi.ui.gps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fbi.R

class GPSFragment : Fragment() {

    private lateinit var gpsViewModel: GPSViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        gpsViewModel =
                ViewModelProviders.of(this).get(GPSViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gps, container, false)
        val textView: TextView = root.findViewById(R.id.text_gps)
        gpsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
