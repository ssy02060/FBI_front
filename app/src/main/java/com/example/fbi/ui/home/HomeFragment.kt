package com.example.fbi.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.fbi.BookList
import com.example.fbi.MainActivity
import com.example.fbi.R
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.viewpager_best5.view.*
import me.relex.circleindicator.CircleIndicator3


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        onSettingBest5Book(root)
        onSettingRecommendBook(root)
        onSettingUserBook(root)

        return root
    }

    private fun onSettingBest5Book(root : View) {

        val items = onSettingBest5BookItem()

        var viewPager2: ViewPager2 = root.findViewById(R.id.viewPager2)
        val adapter = Best5ViewPagerAdapter(
            childFragmentManager,
            items
        )

        viewPager2.adapter = adapter
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2.offscreenPageLimit = 2 //양쪽에 2개씩 총 5개의 페이지 생김
        viewPager2.currentItem = 2 //처음에 중앙에 올 아이템 position


        val indicator: CircleIndicator3 = root.findViewById(R.id.indicator)
        indicator.setViewPager(viewPager2)
       // adapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
        indicator.createIndicators(5,2);

//        val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
//        val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()

        val width = resources.displayMetrics.widthPixels

        val pageMargin = 20 //viewpager2 들어있는 LinearLayout margin
        val img_width = 70
        viewPager2.setPageTransformer { page, position ->
            //            val myOffset = position * -(2 * pageOffset + pageMargin)
            val x = (width-(pageMargin*2))/4*3
            val myOffset = position * - x
            //Log.e("width값 $width x값", x.toString())

            if (position <= -2) {
                val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                page.translationX = myOffset // 기본 위치에서 n만큼 이동
                //Log.e("위치-2", myOffset.toString())
                page.scaleX = 1f
                page.scaleY = 1f
                page.best5_bookImg.alpha = scaleFactor -0.3f // alpha : 투명도
                page.elevation = 6.0f; //elevation: 그림자 효과
            } else if (position <= -1) {
                val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                page.translationX = myOffset
                page.scaleX = 1f
                page.scaleY = 1f
                page.best5_bookImg.alpha = 0.6f//fade in/out
                page.elevation = 7.0f;
                //Log.e("위치-1", myOffset.toString())
            } else if (position <= 0) {
                //Log.e("초기위치 $position $width", page.x.toString())
                val scaleFactor = Math.max(0.9f, 1 - Math.abs(position - 0.14285715f))
                page.translationX = myOffset
                page.scaleX = 1.3f
                page.scaleY = 1.3f
                page.best5_bookImg.alpha = 1f
                page.elevation = 2f;
               // Log.e("위치0", myOffset.toString())
            } else if (position <= 1) {
                val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                page.translationX = myOffset
                page.scaleX = 1f
                page.scaleY = 1f
                page.best5_bookImg.alpha = 0.6f
                page.elevation = 7.0f;
               // Log.e("위치 1", myOffset.toString())
            } else {
                val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                page.translationX = myOffset
                page.scaleX = 1f
                page.scaleY = 1f
                page.best5_bookImg.alpha = 0.3f
                page.elevation = 6.0f;
               // Log.e("위치 2", page.x.toString())
            }
        }

        //아이템 클릭 이벤트
        adapter.itemClick = object: Best5ViewPagerAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(activity, items[position].title, Toast.LENGTH_SHORT).show()
                //가운데 아이템 클릭시 뷰 오류
            }
        }
    }

    private fun onSettingBest5BookItem(): List<MainActivity.best5_Item> {

        val items: List<MainActivity.best5_Item> = listOf(
            MainActivity.best5_Item("홍길동", R.drawable.img_book),
            MainActivity.best5_Item("이순신", R.drawable.img_book2),
            MainActivity.best5_Item("강감찬", R.drawable.img_book3),
            MainActivity.best5_Item("을지문덕", R.drawable.img_book4),
            MainActivity.best5_Item("광개토대왕", R.drawable.img_book5)
        )
        return items
    }

    private fun onSettingRecommendBook(root: View) {

        val items = onSettingRecommendBookItem()
        val recyclerview = root.findViewById(R.id.rv_home_recommend_book_list) as RecyclerView
        val recommendBookListAdapter = RecommendBookListAdapter(activity!!, items)

        recyclerview.layoutManager = LinearLayoutManager(activity)
        recyclerview.adapter = recommendBookListAdapter

        //아이템 클릭 이벤트
        recommendBookListAdapter.itemClick = object: RecommendBookListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(activity, position.toString(), Toast.LENGTH_SHORT).show()
//                if(view.isSelected)
//                    btn_store_book.visibility = View.GONE
//                else
//                    btn_store_book.visibility = View.VISIBLE
            }
        }
    }

    private fun onSettingRecommendBookItem(): ArrayList<BookList> {
        var items = arrayListOf<BookList>(
            BookList("책 제목일까", "저자명이다", "한빛출판사", "2017.09.13", "img_book"),
            BookList("책이란 뭘까", "최지우", "지우출판사", "2017.03.11", "img_book2"),
            BookList("도서는 이것", "서상윤", "상윤출판사", "2018.09.13", "img_book3")
            )
        return items
    }

    private fun onSettingUserBook(root: View?) {
        val items = onSettingUserBookItem()
        var userBookListAdapter = UserBookListAdapter(activity!!, items)
        var recyclerView = root!!.findViewById<RecyclerView>(R.id.rv_home_user_book_list)
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.adapter = userBookListAdapter

        //아이템 클릭 이벤트
        userBookListAdapter.itemClick = object: UserBookListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(activity, position.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onSettingUserBookItem(): ArrayList<BookList> {
        var items = arrayListOf<BookList>(
            BookList("도서를 많이 가지고 있다", "박종욱", "상윤출판사", "2017.09.13", "img_book4"),
            BookList("책책책 책을 읽읍시다", "이호원", "상윤출판사", "2017.05.23", "img_book5"),
            BookList("아니야", "허창환", "상윤출판사", "2019.09.13", "img_book4")
        )
        return items
    }
}
