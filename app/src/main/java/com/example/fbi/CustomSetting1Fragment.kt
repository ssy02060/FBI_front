package com.example.fbi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_custom_setting1.*

class CustomSetting1Fragment: Fragment(), View.OnClickListener {

    var white :Int = 0
    var black :Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_custom_setting1, container, false)

        white = ContextCompat.getColor(activity!!, R.color.white)
        black = ContextCompat.getColor(activity!!, R.color.black)

        //버튼 클릭 이벤트 리스너 설정
        var btnCustomFemale : Button = root.findViewById(R.id.btn_custom_female)
        btnCustomFemale.setOnClickListener(this)
        var btnCustomMale : Button = root.findViewById(R.id.btn_custom_male)
        btnCustomMale.setOnClickListener(this)
        var btnCustomsetAge10 : Button = root.findViewById(R.id.btn_customSet_age_10)
        btnCustomsetAge10.setOnClickListener(this)
        var btnCustomsetAge20 : Button = root.findViewById(R.id.btn_customSet_age_20)
        btnCustomsetAge20.setOnClickListener(this)
        var btnCustomsetAge30 : Button = root.findViewById(R.id.btn_customSet_age_30)
        btnCustomsetAge30.setOnClickListener(this)
        var btnCustomsetAge40 : Button = root.findViewById(R.id.btn_customSet_age_40)
        btnCustomsetAge40.setOnClickListener(this)
        var btnCustomsetEnd : Button = root.findViewById(R.id.btn_customSet_end)
        btnCustomsetEnd.setOnClickListener(this)
        var btnCustomsetNext : Button = root.findViewById(R.id.btn_customSet_next)
        btnCustomsetNext.setOnClickListener(this)

        return root
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btn_custom_female -> {
                if(btn_custom_male.isSelected) {
                    btn_custom_male.isSelected = false
                    btn_custom_male.setTextColor(black)
                }
                btn_custom_female.isSelected = true
                btn_custom_female.setTextColor(white)

            }
            R.id.btn_custom_male -> {
                if(btn_custom_female.isSelected) {
                    btn_custom_female.isSelected = false
                    btn_custom_female.setTextColor(black)
                }
                btn_custom_male.isSelected = true
                btn_custom_male.setTextColor(white)
            }
            R.id.btn_customSet_age_10 -> {
                checkClickAge()
                btn_customSet_age_10.isSelected = true
                btn_customSet_age_10.setTextColor(white)
            }
            R.id.btn_customSet_age_20 -> {
                checkClickAge()
                btn_customSet_age_20.isSelected = true
                btn_customSet_age_20.setTextColor(white)
            }
            R.id.btn_customSet_age_30 -> {
                checkClickAge()
                btn_customSet_age_30.isSelected = true
                btn_customSet_age_30.setTextColor(white)
            }
            R.id.btn_customSet_age_40 -> {
                checkClickAge()
                btn_customSet_age_40.isSelected = true
                btn_customSet_age_40.setTextColor(white)
            }
            R.id.btn_customSet_end -> {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_customSet_next -> {

                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.frame_sign_up, CustomSetting2Fragment())
                transaction?.addToBackStack(null)
                transaction?.commit()

            }
        }
    }

    private fun checkClickAge(){

        if(btn_customSet_age_10.isSelected||btn_customSet_age_20.isSelected||btn_customSet_age_30.isSelected||btn_customSet_age_40.isSelected){
            btn_customSet_age_10.isSelected = false
            btn_customSet_age_20.isSelected = false
            btn_customSet_age_30.isSelected = false
            btn_customSet_age_40.isSelected = false
            btn_customSet_age_10.setTextColor(black)
            btn_customSet_age_20.setTextColor(black)
            btn_customSet_age_30.setTextColor(black)
            btn_customSet_age_40.setTextColor(black)
        }
    }
}