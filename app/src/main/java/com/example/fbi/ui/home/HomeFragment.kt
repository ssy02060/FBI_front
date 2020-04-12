package com.example.fbi.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.example.fbi.MainActivity
import com.example.fbi.MyAdapter
import com.example.fbi.R
import me.relex.circleindicator.CircleIndicator


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
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        var viewPager2: ViewPager2 = root!!.findViewById(R.id.viewPager2)


        val adapter = MyAdapter(childFragmentManager, items)

        viewPager2.adapter = adapter
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2.offscreenPageLimit = 3
        viewPager2.currentItem = 1000

        val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
        val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()

        viewPager2.setPageTransformer { page, position ->
            val myOffset = position * -(2 * pageOffset + pageMargin)
            if (position < -2) {
                page.translationX = -myOffset
            } else if (position <= -1) {
                val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
            } else if (position <= 0) {
                val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
            } else if (position <= 1) {
                val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
            } else {
                val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
            }
        }
        return root
    }

}
