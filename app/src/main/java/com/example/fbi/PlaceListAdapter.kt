package com.example.fbi

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class PlaceListAdapter(
    val context: Context,
    val placeList: ArrayList<PlaceList>
) :
    RecyclerView.Adapter<PlaceListAdapter.Holder>(), Filterable {

    var filteredList: ArrayList<PlaceList>? = null //필터링된 아이템 리스트
    var index : Int = -1 //아이템 배경색 변경 위한 인덱스 저장
    var filter_option : Int = 0

    interface ItemClick
    {
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null


    init{
        filteredList = placeList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_place_list_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return filteredList!!.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = filteredList!![position]
        holder?.bind(filteredList!![position], context)

        //아이템 클릭 이벤트
        holder.itemView.setOnClickListener(View.OnClickListener {
            index = position
            notifyDataSetChanged() //배경 색 변경된 데이터 리스트에 적용
            itemClick?.onClick(it, position) //SearchActivity의 클릭이벤트 호출
        })
        var color :Int = ContextCompat.getColor(context, R.color.colorSelected)
        //선택된 아이템 처리
        if (index === position) {

            if(holder.itemView.isSelected) {
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
                holder.itemView.isSelected = false
            }
            else{
                holder.itemView.setBackgroundColor(color)
                holder.itemView.isSelected = true
            }

        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
            holder.itemView.isSelected = false
        }
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val place_title = itemView?.findViewById<TextView>(R.id.tv_rv_place_title)
        val place_address = itemView?.findViewById<TextView>(R.id.tv_rv_place_address)
        val place_distance = itemView?.findViewById<TextView>(R.id.tv_rv_place_distance)

        fun bind (placelist: PlaceList, context: Context) {

            place_title?.text = placelist.title
            place_address?.text = placelist.address
            place_distance?.text = placelist.distance

        }
    }

//    override fun getFilter(): Filter? {
//            return object : Filter() {
//            override fun performFiltering(charSequence: CharSequence): FilterResults {
//                val charString = charSequence.toString() //입력된 string
//                if (charString.isEmpty()) {//입력된 문자 없을 경우
//                    filteredList = placeList //필터링 되지 않은 리스트를 필터링된 리스트로 사용
//                } else {
//                    val filteringList = ArrayList<BookList>()
//                    //일치하는 케이스 검색해서 필터링 중인 리스트(filteringList)에 넣음
//                    for (row in placeList) {
//                        when (filter_option) {
//                            0 -> { //전체 검색
//                                if (row.title.toLowerCase().contains(charString.toLowerCase()) || row.author.toLowerCase().contains(charString.toLowerCase())
//                                    || row.publisher.toLowerCase().contains(charString.toLowerCase())) {
//                                    Log.e("필터링",row.title)
//                                    filteringList.add(row)
//                                }
//                            }
//                            1 -> { //도서명으로 검색
//                                if (row.title.toLowerCase().contains(charString.toLowerCase())) {
//                                    Log.e("필터링",row.title)
//                                    filteringList.add(row)
//                                }
//                            }
//                            2 -> { //저자명으로 검색
//                                if (row.author.toLowerCase().contains(charString.toLowerCase())) {
//                                    Log.e("필터링",row.title)
//                                    filteringList.add(row)
//                                }
//                            }
//                            3 -> { //출판사명으로 검색
//                                if (row.publisher.toLowerCase().contains(charString.toLowerCase())) {
//                                    Log.e("필터링",row.title)
//                                    filteringList.add(row)
//                                }
//                            }
//                        }
//                    }
//                    filteredList = filteringList //필터링 중인 리스트를 필터링 된 리스트로 사용
//                }
//                val filterResults = FilterResults()
//                filterResults.values = filteredList //필터링 된 리스트 넘겨줌
//                return filterResults
//            }
//            //필터링 완료 후 recyclerview 업데이트해줌
//            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
//                filteredList = filterResults.values as ArrayList<BookList>
//                notifyDataSetChanged()
//            }
//        }
//    }

    fun setOption(option : Int){
        filter_option = option
    }

    override fun getFilter(): Filter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}