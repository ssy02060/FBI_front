package com.example.fbi.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fbi.R

class MyPageFragment : Fragment() {

    private lateinit var mypageViewModel: MyPageViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mypageViewModel =
                ViewModelProviders.of(this).get(MyPageViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mypage, container, false)
        val textView: TextView = root.findViewById(R.id.text_mypage)
        mypageViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
