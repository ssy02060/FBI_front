package com.example.fbi.ui.bookshelf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fbi.R

class BookshelfFragment : Fragment() {

    private lateinit var bookshelfViewModel: BookshelfViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        bookshelfViewModel =
                ViewModelProviders.of(this).get(BookshelfViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_bookshelf, container, false)
        val textView: TextView = root.findViewById(R.id.text_bookshelf)
        bookshelfViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
