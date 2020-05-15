package com.example.fbi.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fbi.MainActivity
import com.example.fbi.R
import kotlinx.android.synthetic.main.viewpager_best5.view.*

class Best5ViewPagerAdapter(
    private val context: FragmentManager,
    private val list: List<MainActivity.best5_Item>) :
    RecyclerView.Adapter<Best5ViewPagerAdapter.MyViewHolder>() {

    //클릭 이벤트
    interface ItemClick
    {
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewpager_best5, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val index = position % list.size
        holder.bind(list[index])

        holder.itemView.setOnClickListener(View.OnClickListener {
            itemClick?.onClick(it, position) //SearchActivity의 클릭이벤트 호출
        })
    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
       // var best5_bookTitle: TextView
        var best5_bookImg: ImageView
       // var best5_backImg: ImageView
        init {
            //best5_bookTitle = itemView.findViewById(R.id.best5_bookTitle)
            best5_bookImg = itemView.findViewById(R.id.best5_bookImg)
            //best5_backImg = itemView.findViewById(R.id.best5_backImg)
        }
        fun bind(item: MainActivity.best5_Item) {
            //itemView.best5_bookTitle.text = "${item.title}"
            itemView.best5_bookImg.setImageResource(item.img)
        }
    }

}
