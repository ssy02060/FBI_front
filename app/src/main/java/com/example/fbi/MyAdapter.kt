package com.example.fbi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_item.view.*

class MyAdapter(private val context: FragmentManager,private val list: List<MainActivity.Item>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val index = position % list.size
        holder.bind(list[index])
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvName: TextView
        var imgBanner: ImageView

        init {
            tvName = itemView.findViewById(R.id.tvName)
            imgBanner = itemView.findViewById(R.id.imgBanner)
        }
        fun bind(item: MainActivity.Item) {
            itemView.tvName.text = "${item.title}"
            itemView.imgBanner.setImageResource(item.icon)
        }
    }

}
