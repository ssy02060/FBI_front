package com.example.fbi

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.fbi.ui.mypage.MyPageViewModel
import kotlinx.android.synthetic.main.fragment_custom_setting1.*
import kotlinx.android.synthetic.main.fragment_custom_setting2.*

class CustomSetting2Fragment: Fragment(), View.OnClickListener {

    var white :Int = 0
    var black :Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_custom_setting2, container, false)

        white = ContextCompat.getColor(activity!!, R.color.white)
        black = ContextCompat.getColor(activity!!, R.color.black)

        //버튼 클릭 이벤트 리스너 설정
        var btn_category_history : Button = root.findViewById(R.id.btn_category_history)
        btn_category_history.setOnClickListener(this)
        var btn_category_psychology : Button = root.findViewById(R.id.btn_category_psychology)
        btn_category_psychology.setOnClickListener(this)
        var btn_category_essay : Button = root.findViewById(R.id.btn_category_essay)
        btn_category_essay.setOnClickListener(this)
        var btn_category_humanities : Button = root.findViewById(R.id.btn_category_humanities)
        btn_category_humanities.setOnClickListener(this)
        var btn_category_science : Button = root.findViewById(R.id.btn_category_science)
        btn_category_science.setOnClickListener(this)
        var btn_category_detective : Button = root.findViewById(R.id.btn_category_detective)
        btn_category_detective.setOnClickListener(this)
        var btn_customSet_category_end : Button = root.findViewById(R.id.btn_customSet_category_end)
        btn_customSet_category_end.setOnClickListener(this)
        var btn_customSet_category_finish : Button = root.findViewById(R.id.btn_customSet_category_finish)
        btn_customSet_category_finish.setOnClickListener(this)
        var btn_category_history_cover : Button = root.findViewById(R.id.btn_category_history_cover)
        btn_category_history_cover.setOnClickListener(this)
        var btn_category_psychology_cover : Button = root.findViewById(R.id.btn_category_psychology_cover)
        btn_category_psychology_cover.setOnClickListener(this)
        var btn_category_essay_cover : Button = root.findViewById(R.id.btn_category_essay_cover)
        btn_category_essay_cover.setOnClickListener(this)
        var btn_category_humanities_cover : Button = root.findViewById(R.id.btn_category_humanities_cover)
        btn_category_humanities_cover.setOnClickListener(this)
        var btn_category_science_cover : Button = root.findViewById(R.id.btn_category_science_cover)
        btn_category_science_cover.setOnClickListener(this)
        var btn_category_detective_cover : Button = root.findViewById(R.id.btn_category_detective_cover)
        btn_category_detective_cover.setOnClickListener(this)

        return root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_category_history -> {
                btn_category_history.isSelected = true
                btn_category_history_cover.visibility = View.VISIBLE
            }
            R.id.btn_category_psychology -> {
                btn_category_psychology.isSelected = true
                btn_category_psychology_cover.visibility = View.VISIBLE
            }
            R.id.btn_category_essay -> {
                btn_category_essay.isSelected = true
                btn_category_essay_cover.visibility = View.VISIBLE
            }
            R.id.btn_category_humanities -> {
                btn_category_humanities.isSelected = true
                btn_category_humanities_cover.visibility = View.VISIBLE
            }
            R.id.btn_category_science -> {
                btn_category_science.isSelected = true
                btn_category_science_cover.visibility = View.VISIBLE
            }
            R.id.btn_category_detective -> {
                btn_category_detective.isSelected = true
                btn_category_detective_cover.visibility = View.VISIBLE
            }
            R.id.btn_category_history_cover -> {
                btn_category_history.isSelected = false
                btn_category_history_cover.visibility = View.INVISIBLE
            }
            R.id.btn_category_psychology_cover -> {
                btn_category_psychology.isSelected = false
                btn_category_psychology_cover.visibility = View.INVISIBLE
            }
            R.id.btn_category_essay_cover -> {
                btn_category_essay.isSelected = false
                btn_category_essay_cover.visibility = View.INVISIBLE
            }
            R.id.btn_category_humanities_cover -> {
                btn_category_humanities.isSelected = false
                btn_category_humanities_cover.visibility = View.INVISIBLE
            }
            R.id.btn_category_science_cover -> {
                btn_category_science.isSelected = false
                btn_category_science_cover.visibility = View.INVISIBLE
            }
            R.id.btn_category_detective_cover -> {
                btn_category_detective.isSelected = false
                btn_category_detective_cover.visibility = View.INVISIBLE
            }
            R.id.btn_customSet_category_end -> {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_customSet_category_finish -> {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}