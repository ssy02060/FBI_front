package com.example.fbi

import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.sql.*
import java.util.logging.Logger


class LoginActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null //파이어베이스 인증 객체
    private var googleSigninClient : GoogleSignInClient? = null //구글 로그인 클라이언트 객체
    private val RC_SIGN_IN = 9001 // 구글로그인 결과 코드

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        try {
            val Log = Logger.getLogger(MainActivity::class.java.name)
            val info = packageManager.getPackageInfo("com.example.fbi", PackageManager.GET_SIGNATURES);

            for(signature in info.signatures) {

                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())

                Log.warning("KeyHash : ${Base64.encodeToString(md.digest(), Base64.DEFAULT)}")

            }

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch ( e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
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
                    //Toast.makeText(this, acct.email, Toast.LENGTH_SHORT).show()
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
    //카카오 로그인
    private fun connectionJDBCTest() : String {
        var sb = StringBuffer()
        var con : Connection? = null
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (e : ClassNotFoundException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            e.printStackTrace();
        }
        // 만약 위와 같이 Driver를 설정하지 않는다면, Driver를 찾을수 없다는 Error 메세지가 발생될 것 입니다.
        try {
            con = DriverManager.getConnection("jdbc:mysql://27.113.21.252:8462","Frontmanager","seojieun");
            // 접속하고자 하는 Maria DB Server가 설치된 IP 주소 및 설정되어 있는 ID와 PassWord를 설정.
            var st : Statement? = null
            var rs : ResultSet? = null

            st = con.createStatement()
            if(st.execute("SHOW DATABASES")) {
                rs = st.getResultSet();
            }
            while(rs!!.next()) {
                var str : String = rs.getString(1);
                sb.append(str);
                sb.append("\n");
            }
        } catch (e : SQLException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            e.printStackTrace();
        } finally {
            if(null != con) {
                try {
                    con.close();
                } catch (e : SQLException) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString()
    }
}

class JSONTask : AsyncTask<String, String, String>() {
    override fun doInBackground(vararg urls: String?): String? {
        try { //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
            val jsonObject = JSONObject()
            jsonObject.accumulate("user_id", "androidTest")
            jsonObject.accumulate("name", "yun")
            var con: HttpURLConnection? = null
            var reader: BufferedReader? = null
            try { //URL url = new URL("http://192.168.25.16:3000/users");
                val url = URL(urls[0]) //url을 가져온다.
                con = url.openConnection() as HttpURLConnection
                con.connect() //연결 수행
                //입력 스트림 생성
                val stream: InputStream = con.getInputStream()
                //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
                reader = BufferedReader(InputStreamReader(stream))
                //실제 데이터를 받는곳
                val buffer = StringBuffer()
                //line별 스트링을 받기 위한 temp 변수
                var line: String? = ""
                //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
                while (reader.readLine().also({ line = it }) != null) {
                    buffer.append(line)
                }
                //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                return buffer.toString()
                //아래는 예외처리 부분이다.
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally { //종료가 되면 disconnect메소드를 호출한다.
                if (con != null) {
                    con.disconnect()
                }
                try { //버퍼를 닫아준다.
                    if (reader != null) {
                        reader.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } //finally 부분
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}