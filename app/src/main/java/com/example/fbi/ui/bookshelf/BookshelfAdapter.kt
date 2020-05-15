package com.example.fbi.ui.bookshelf

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.fbi.BookList
import com.example.fbi.R
import kotlinx.android.synthetic.main.bookshelf_item.view.*


class BookshelfAdapter(
    private val context: Context,
    private val booklist: ArrayList<BookList>) :
    BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        var view = convertView

        val book = booklist[position]
        if (view == null) {
            val inflater = context?.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.bookshelf_item, null)
        }

        //아이템 이미지 설정
        val resourceId = context.resources.getIdentifier(book.img, "drawable", context.packageName)
        view?.iv_bookshelf_book_img?.setImageResource(resourceId)

        return view
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return booklist.size
    }


}