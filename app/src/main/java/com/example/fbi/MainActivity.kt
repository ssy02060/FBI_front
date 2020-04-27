package com.example.fbi

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebViewFragment
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isInvisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.fbi.ui.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.*
import kotlinx.android.synthetic.main.main.*


class MainActivity : AppCompatActivity() {

    data class best5_Item(val title: String, @DrawableRes val img: Int)
    var menuItem: MenuItem? = null
    var nickname: String? = null
    var email: String? = null
    var photoURL: String? = null
    var mCtx : Context? = null //search 에서 쓰이는데 지금 안씀

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        mCtx = this //context 값 할당 (context 못가져오는 곳에서 쓰일 Context변수)
        //getAccountInfoFromLoginActivity() //로그인 오류 해결 전 주석처리

        //setting toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setDisplayShowTitleEnabled(false) //기존 appbar title

        val navView: BottomNavigationView = findViewById(R.id.bottom_nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // 각 fragemnt들을 최상위 대상으로 설정
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_gps,
                R.id.navigation_camera,
                R.id.navigation_bookshelf,
                R.id.navigation_mypage,
                R.id.navigation_search
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //샌드위치 메뉴 누르면 드로어 레이아웃 열기
        btn_userinfo?.setOnClickListener {
            main_layout.openDrawer(Gravity.LEFT)
            //드로우어가 오픈된 후 drawer의 인자를 login Activity에서 받아온 데이터로 입력
            tv_nickname.text = nickname
            tv_email.text = email
            Glide.with(this).load(photoURL).into(iv_profile)
            //드로어 열린 후 logout 버튼 클릭 처리
            btn_drawer_logout.setOnClickListener {
                Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show()
            }
        }

        btn_search?.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.navigation_search)
            btn_search?.isInvisible = true
        }
        settingPermission() // 카메라 권한 체크 시작
    }

    //로그인 오류 해결 전 주석처리
    //유저의 정보를 loginActivity로부터 받아오는 함수
    private fun getAccountInfoFromLoginActivity() {
        if (intent.hasExtra("nickName")) {
            nickname = intent.getStringExtra("nickName")
            /* "nickName"라는 이름의 key에 저장된 값이 있다면
               textView의 내용을 "nickName" key에서 꺼내온 값으로 바꾼다 */
        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }
        if (intent.hasExtra("email")) {
            email = intent.getStringExtra("email")
            /* "nickName"라는 이름의 key에 저장된 값이 있다면
               textView의 내용을 "nickName" key에서 꺼내온 값으로 바꾼다 */

        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }
        if (intent.hasExtra("photoURL")) {
            photoURL = intent.getStringExtra("photoURL")
            /* "nickName"라는 이름의 key에 저장된 값이 있다면
               textView의 내용을 "nickName" key에서 꺼내온 값으로 바꾼다 */
        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() { //뒤로가기 처리
        if (main_layout.isDrawerOpen(GravityCompat.START)) {
            main_layout.closeDrawers()
            // 테스트를 위해 뒤로가기 버튼시 Toast 메시지
            Toast.makeText(this, "back btn clicked", Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
    }

    //권한 설정을 하는 함수
    fun settingPermission() {
        var permis = object : PermissionListener {
            //어떠한 형식을 상속받는 익명 클래스의 객체를 생성하기 위해 다음과 같이 작성
            override fun onPermissionGranted() {
                Toast.makeText(this@MainActivity, "권한 허가", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@MainActivity, "권한 거부", Toast.LENGTH_SHORT)
                    .show()
                ActivityCompat.finishAffinity(this@MainActivity) // 권한 거부시 앱 종료
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permis)
            .setRationaleMessage("카메라 사진 권한 필요")
            .setDeniedMessage("카메라 권한 요청 거부")
            .setPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
            )
            .check()
    }
}
