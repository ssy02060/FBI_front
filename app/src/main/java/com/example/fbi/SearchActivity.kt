package com.example.fbi

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fbi.ui.home.BookListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity() {

    var mCtx : Context? = null
    private var bookList: ArrayList<BookList> = ArrayList()
    private var recyclerView: RecyclerView? = null
    private var bookListAdapter: BookListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mCtx = this //context 값 할당 (context 못가져오는 곳에서 쓰일 Context변수)

        setSupportActionBar(findViewById(R.id.toolbar_in_search))
        supportActionBar?.setDisplayShowTitleEnabled(false) //기존 appbar title
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //뒤로가기 버튼 보이게
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_arrow);//뒤로가기 버튼 아이콘 변경

        onSettingBookItem() // 도서 리스트 불러오는 함수
        onStoreBook() //탐색 도서 저장 처리 함수

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater = menuInflater
        menuInflater.inflate(R.menu.search_menu, menu)

        var searchItem = menu?.findItem(R.id.action_search) ?: return false
        var searchView = searchItem.actionView as SearchView
        searchView.layoutParams = ActionBar.LayoutParams(Gravity.RIGHT)
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.queryHint = "검색어를 입력해주세요"
        //searchView.setIconifiedByDefault(true)
        searchView.clearFocus() //키보드 열림
        searchView.setQuery("", false);
        searchView.isIconified = false // 바로 텍스트 입력할 수 있도록 검색창 열어둠

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

            //텍스트 검색시 호출
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(mCtx?.applicationContext, query + "를 검색합니다.", Toast.LENGTH_LONG)
                    .show()
                return true
            }

            //텍스트 바뀔때마다 호출
            override fun onQueryTextChange(newText: String): Boolean {
                bookListAdapter?.getFilter()?.filter(newText)
                return true
            }
        })
        return true
    }

    //뒤로가기 버튼 처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()//현재 액티비티 종료
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onStoreBook(){

        bookListAdapter = BookListAdapter(this, bookList)
        recyclerView = findViewById<RecyclerView>(R.id.rv_book_list)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.adapter = bookListAdapter

        //아이템 클릭 이벤트
        bookListAdapter!!.itemClick = object: BookListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                //Toast.makeText(mCtx, position.toString(), Toast.LENGTH_SHORT).show()
                if(view.isSelected)
                    btn_store_book.visibility = View.GONE
                else
                btn_store_book.visibility = View.VISIBLE
            }
        }

        //탐색 도서 저장 버튼 클릭 시
        btn_store_book?.setOnClickListener {
            //DB에 사용자 탐색 도서 정보 저장
        }
    }


    private fun onSettingBookItem() {

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

}
