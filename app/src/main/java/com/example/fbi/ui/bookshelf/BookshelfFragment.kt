package com.example.fbi.ui.bookshelf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fbi.BookList
import com.example.fbi.R
import com.example.fbi.ui.camera.CheckBookDialogFragment
import kotlinx.android.synthetic.main.fragment_bookshelf.*


class BookshelfFragment : Fragment() {

    private lateinit var bookshelfViewModel: BookshelfViewModel
    private var bookList: ArrayList<BookList> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bookList = generateBookData()
//        val gridView = findViewById(R.id.gridview_bookshelf) as GridView
//        val bookshelfAdapter = BookshelfAdapter(activity!!, bookList)
//        gridview_bookshelf.adapter = bookshelfAdapter
//
//        gridview_bookshelf.onItemClickListener =
//            OnItemClickListener { parent, view, position, id ->
//                val book: BookList = bookList[position]
//                Toast.makeText(context?.applicationContext, "선택.", Toast.LENGTH_SHORT)
//                bookshelfAdapter.notifyDataSetChanged()
//            }
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        bookshelfViewModel =
                ViewModelProviders.of(this).get(BookshelfViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_bookshelf, container, false)
//        val textView: TextView = root.findViewById(R.id.text_bookshelf)
        bookshelfViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
        })

        val gridView = root.findViewById(R.id.gridview_bookshelf) as GridView
        val bookshelfAdapter = BookshelfAdapter(activity!!, bookList)
        gridView.adapter = bookshelfAdapter

        gridView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val book: BookList = bookList[position]
                var dialog = BookshelfDialogFragment();
                dialog.show(this.childFragmentManager!!,"tag");
                bookshelfAdapter.notifyDataSetChanged()
            }

        return root
    }

    private fun generateBookData(): ArrayList<BookList> {

        bookList = arrayListOf<BookList>(
            BookList("책 제목일까", "저자명이다", "한빛출판사", "2017.09.13", "img_book"),
            BookList("책이란 뭘까", "최지우", "지우출판사", "2017.03.11", "img_book2"),
            BookList("도서는 이것", "서상윤", "상윤출판사", "2018.09.13", "img_book3"),
            BookList("도서를 많이 가지고 있다", "박종욱", "상윤출판사", "2017.09.13", "img_book4"),
            BookList("책책책 책을 읽읍시다", "이호원", "상윤출판사", "2017.05.23", "img_book5"),
            BookList("아니야", "허창환", "상윤출판사", "2019.09.13", "img_book4"),
            BookList("도서를 많이 가지고 있다", "박종욱", "상윤출판사", "2017.09.13", "img_book4"),
            BookList("책책책 책을 읽읍시다", "이호원", "상윤출판사", "2017.05.23", "img_book5"),
            BookList("아니야", "허창환", "상윤출판사", "2019.09.13", "img_book4"),
            BookList("도서를 많이 가지고 있다", "박종욱", "상윤출판사", "2017.09.13", "img_book4"),
            BookList("책책책 책을 읽읍시다", "이호원", "상윤출판사", "2017.05.23", "img_book5"),
            BookList("아니야", "허창환", "상윤출판사", "2019.09.13", "img_book4"),
            BookList("맞아", "예효은", "상윤출판사", "2020.09.13", "img_book3")
        )
        return bookList
    }
}
