package com.example.fbi

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main.*
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator


class MainActivity : AppCompatActivity(){

    data class Item(val title: String, @DrawableRes val img: Int)

    var menuItem: MenuItem? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        //setting toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

        //home navigation (툴바에 홈버튼 활성화)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_userinfo)

        //supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_white) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        //supportActionBar?.setDisplayShowTitleEnabled(false)
//        // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_userinfo);

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_gps, R.id.navigation_camera, R.id.navigation_bookshelf, R.id.navigation_mypage))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val actionBar = supportActionBar!!
        actionBar.setDisplayShowTitleEnabled(false)
        //드로어 안에 로그아웃 버튼 눌러도 반응 없음

        //샌드위치 메뉴 누르면 드로어 레이아웃 열기
        btn_userinfo?.setOnClickListener{
            main_layout.openDrawer(Gravity.LEFT)
        }


    }



    override fun onCreateOptionsMenu(menu: Menu):  Boolean {
        var menuInflater = menuInflater
        menuInflater.inflate(R.menu.top_menu, menu)
//        searchview
        var searchItem = menu.findItem(R.id.action_search) ?: return false
        var searchView = searchItem.actionView as SearchView
        searchView.layoutParams = ActionBar.LayoutParams(Gravity.RIGHT)
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setIconifiedByDefault (true)
        searchView.clearFocus();
        searchView.setQuery("", false);
        searchView.setIconified(true);

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String) = false
                override fun onQueryTextChange(newText: String): Boolean {
                    //textView.text = newText
                    return true
                }
            }
        )



        // Associate searchable configuration with the SearchView
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        (menu?.findItem(R.id.action_search)?.actionView as SearchView).apply {
//            setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        }


        return true
    }

//    override fun onSearchRequested(): Boolean {
//
//        return super.onSearchRequested()
//    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            //아이템 아니라 로그아웃 버튼 해둠 ..
//            R.id.drawer_logout-> Toast.makeText(this,"logout clicked",Toast.LENGTH_SHORT).show()
//        }
//        return false
//    }

    //액션버튼 클릭 했을 때
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_search -> {
                //검색 버튼 눌렀을 때
                onSearchRequested()
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)

    }



    override fun onBackPressed() { //뒤로가기 처리
        if(main_layout.isDrawerOpen(GravityCompat.START)){
            main_layout.closeDrawers()
            // 테스트를 위해 뒤로가기 버튼시 Toast 메시지
            Toast.makeText(this,"back btn clicked",Toast.LENGTH_SHORT).show()
        } else{
            super.onBackPressed()
        }
    }


}
