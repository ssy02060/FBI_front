package com.example.fbi.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.example.fbi.Best5ViewPagerAdapter
import com.example.fbi.MainActivity
import com.example.fbi.R
import kotlinx.android.synthetic.main.viewpager_best5.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    var viewpager2: ViewPager2? = null

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val items: List<MainActivity.Item> = listOf(
            MainActivity.Item("홍길동", R.drawable.img_book),
            MainActivity.Item("이순신", R.drawable.img_book2),
            MainActivity.Item("강감찬", R.drawable.img_book3),
            MainActivity.Item("을지문덕", R.drawable.img_book4),
            MainActivity.Item("광개토대왕", R.drawable.img_book5)
        )
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root:ViewGroup = inflater.inflate(R.layout.fragment_home, container, false) as ViewGroup

        var viewPager2: ViewPager2 = root.findViewById(R.id.viewPager2)
        val adapter = Best5ViewPagerAdapter(childFragmentManager, items)

        viewPager2.adapter = adapter
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2.offscreenPageLimit = 2 //가운데 아이템 + 양쪽 2개씩 4개,총 5개 만 보여짐
        viewPager2.currentItem = 1000

        val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
        val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()

        viewPager2.setPageTransformer { page, position ->
            val myOffset = position * -(2 * pageOffset + pageMargin)
            if (position < -2) {
                val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                //page.scaleX = 0.8f
                page.scaleY = scaleFactor
                page.translationX =-myOffset

                page.best5_bookImg.alpha = scaleFactor -0.3f
                page.setElevation(6.0f);
            } else if (position <= -1) {
                val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                page.translationX = myOffset
                page.scaleX = 0.9f
                page.scaleY = scaleFactor
                page.best5_bookImg.alpha = scaleFactor//fade in/out
                page.setElevation(7.0f);
            } else if (position <= 0) {
                val scaleFactor = Math.max(0.9f, 1 - Math.abs(position - 0.14285715f))
                page.translationX = myOffset
                page.scaleX = 1.1f
                page.scaleY = scaleFactor
                page.best5_bookImg.alpha = 1f
                page.setElevation(8.0f);
            } else if (position <= 1) {
                val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                page.translationX = myOffset
                page.scaleX = 0.9f
                page.scaleY = scaleFactor
                page.best5_bookImg.alpha = scaleFactor
                page.setElevation(7.0f);
            } else {
                val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.best5_bookImg.alpha = scaleFactor -0.3f
                page.setElevation(6.0f);
            }
        }
        return root
    }
}
