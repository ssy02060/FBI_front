package com.example.fbi.ui.bookshelf

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.fbi.R


//사진에서 도서 찾은 후 찾는 도서가 맞는지 확인하는 팝업
class BookshelfDialogFragment : DialogFragment() {

    private var myListener: MyDialogListener? = null
    private lateinit var mCtx : Context

    interface MyDialogListener {
        fun myCallback(cityName: String?)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.dialog_check_book, container)

        var msg : TextView = view.findViewById(R.id.tv_title_msg);
        var cancel : Button = view.findViewById(R.id.btn_check_book_cancel);
        var ok : Button = view.findViewById(R.id.btn_check_book_ok);

        msg.text = "도서 정보"
        cancel.text = "삭제"
        ok.text = "닫기"
        cancel?.setOnClickListener(View.OnClickListener {
                Toast.makeText(activity, "삭제", Toast.LENGTH_SHORT)
                    .show()
                dialog!!.dismiss()
        })

        ok?.setOnClickListener(View.OnClickListener {
            Toast.makeText(activity, "닫기", Toast.LENGTH_SHORT)
                    .show()
            dialog!!.dismiss()
        })
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myListener = try {
            targetFragment as MyDialogListener?
        } catch (e: ClassCastException) {
            throw ClassCastException()
        }
    }
}