package com.example.fbi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.fbi.ui.mypage.MyPageViewModel
import kotlinx.android.synthetic.main.activity_sign_up_main.*

class SignUpFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_sign_up, container, false)

        //버튼 클릭 처리
        val btn_check_email : TextView = root.findViewById(R.id.btn_check_email)
        btn_check_email.setOnClickListener(this)
        val btn_signUp_end : TextView = root.findViewById(R.id.btn_signUp_end)
        btn_signUp_end.setOnClickListener(this)

        return root
    }


    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_check_email -> {
                Toast.makeText(activity?.applicationContext, "중복 확인", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_signUp_end -> {
                Toast.makeText(activity?.applicationContext, "회원 가입", Toast.LENGTH_SHORT).show()
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.frame_sign_up, CustomSetting1Fragment())
                transaction?.addToBackStack(null)
                transaction?.commit()
            }
            R.id.acco_logout -> {
                Toast.makeText(activity?.applicationContext, "로그아웃", Toast.LENGTH_SHORT).show()
            }
            R.id.guide_help -> {
                Toast.makeText(activity?.applicationContext, "도움말", Toast.LENGTH_SHORT).show()
            }
            R.id.guide_faq -> {
                Toast.makeText(activity?.applicationContext, "자주묻는질문", Toast.LENGTH_SHORT).show()
            }
            R.id.guide_ques -> {
                Toast.makeText(activity?.applicationContext, "문의하기", Toast.LENGTH_SHORT).show()
            }
            R.id.info_tou -> {
                Toast.makeText(activity?.applicationContext, "FBI 이용약관", Toast.LENGTH_SHORT).show()
            }
            R.id.info_privacy -> {
                Toast.makeText(activity?.applicationContext, "개인정보 활용원칙", Toast.LENGTH_SHORT).show()
            }
            R.id.sece_confirm -> {
                Toast.makeText(activity?.applicationContext, "탈퇴하기", Toast.LENGTH_SHORT).show()
            }
        }
    }
}