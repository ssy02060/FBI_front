package com.example.fbi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var auth : FirebaseAuth? = null //파이어베이스 인증 객체
    private var googleSigninClient : GoogleSignInClient? = null //구글 로그인 클라이언트 객체
    private val RC_SIGN_IN = 9001 // 구글로그인 결과 코드

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //[START config_signin]
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        //[END config_signin]
        googleSigninClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener{
            val signInIntent = googleSigninClient?.getSignInIntent()
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }
    //구글 로그인 인증을 요청했을 때 결과값을 돌려받는 곳
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult(ApiException::class.java)  //account 라는 데이터는 구글 로그인 정보를 담고 있음(닉네임, 프로필사진 url, 이메일주소 등)
                if (account != null) {
                    firebaseAuthWithGoogle(account)
                }
            }
            catch (e: ApiException){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    //파이어베이스로부터 인증을 받아와 유저의 정보를 다음 액티비티로 전달하는 함수
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth!!.signInWithCredential(credential)
            .addOnCompleteListener(this){
                if(it.isSuccessful){    //인증결과가 성공적인지
                    val user = auth?.currentUser
                    Toast.makeText(this, acct.email, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)

                    intent.putExtra("nickName", acct.displayName)
                    intent.putExtra("email", acct.email)
                    intent.putExtra("photoURL", acct.photoUrl.toString())

                    startActivity(intent)
                }
                else{       //로그인 실패 시
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }

            }
    }
}
