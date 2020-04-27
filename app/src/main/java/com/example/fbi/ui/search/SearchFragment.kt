package com.example.fbi.ui.search

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fbi.BookList
import com.example.fbi.BookListAdapter
import com.example.fbi.R
import kotlinx.android.synthetic.main.activity_main.*


class SearchFragment : Fragment() {

    var bookList: ArrayList<BookList> = ArrayList()
    private lateinit var searchViewModel: SearchViewModel
    lateinit var recyclerView: RecyclerView
    var bookListAdapter: BookListAdapter? = null
    var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bookList = arrayListOf<BookList>(
            BookList("책 제목일까", "저자명이다", "한빛출판사", "2017.09.13", "img_book"),
            BookList("책이란 뭘까", "최지우", "지우출판사", "2017.03.11", "img_book2"),
            BookList("도서는 이것", "서상윤", "상윤출판사", "2018.09.13", "img_book3"),
            BookList("도서를 많이 가지고 있다", "박종욱", "상윤출판사", "2017.09.13", "img_book4"),
            BookList("책책책 책을 읽읍시다", "이호원", "상윤출판사", "2017.05.23", "img_book5"),
            BookList("아니야", "허창환", "상윤출판사", "2019.09.13", "img_book4"),
            BookList("맞아", "예효은", "상윤출판사", "2020.09.13", "img_book3")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        bookListAdapter = BookListAdapter(requireContext(), bookList)
        recyclerView = root.findViewById(R.id.rv_book_list) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //recyclerView.adapter = BookListAdapter(requireContext(), bookList)
        recyclerView.adapter = bookListAdapter
        setHasOptionsMenu(true);
        return root
    }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.top_menu, menu)
        var searchItem: MenuItem = menu.findItem(R.id.action_search)
        var searchView = searchItem.actionView as SearchView
        searchView.layoutParams = ActionBar.LayoutParams(Gravity.RIGHT)
        var tv_search_width_guide: TextView? = activity?.findViewById(R.id.tv_search_width_guide)
        searchView.maxWidth = tv_search_width_guide?.width!!
        searchView.queryHint = "검색어를 입력해주세요"
        searchView.setIconifiedByDefault(true)
        searchView.clearFocus() //키보드 열림
        searchView.setQuery("", false);
        searchView.isIconified = false
        searchItem.expandActionView()//검색창 열려있도록
        //검색창 버튼 색 변경
        val icon: ImageView = searchView.findViewById(androidx.appcompat.R.id.search_button)
        icon.setColorFilter(Color.WHITE)
        val icon2: ImageView = searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        icon2.setColorFilter(Color.WHITE)

        //검색창 텍스트 색상 변경
        val searchEditText = searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
        searchEditText.setTextColor(resources.getColor(R.color.white))
        searchEditText.setHintTextColor(resources.getColor(R.color.white))

        //검색창 열었을 때
        searchView.setOnSearchClickListener {
            menu_title?.visibility = View.INVISIBLE //툴바의 타이틀 텍스트뷰 사라지게
        }
        //검색창 닫았을 때
        searchView.setOnCloseListener {
            menu_title?.visibility = View.VISIBLE //툴바의 타이틀 텍스트뷰 보이게
            false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            //검색창 열린 상태에서 검색버튼 눌렀을 때 호출

            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(activity?.applicationContext, query + "를 검색합니다.", Toast.LENGTH_LONG)
                    .show()
                return true
            }

            //텍스트 바뀔때마다 호출
            override fun onQueryTextChange(newText: String): Boolean {
                bookListAdapter?.getFilter()?.filter(newText)
                return true
            }
        })

    }
}
