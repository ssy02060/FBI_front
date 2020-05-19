package com.example.fbi.ui.gps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fbi.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_gps.*
import noman.googleplaces.Place
import noman.googleplaces.PlacesException
import noman.googleplaces.PlacesListener

class GPSFragment : Fragment(), PlacesListener {

    private lateinit var gpsViewModel: GPSViewModel

    //---------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------
    //런타임에서 권한이 필요한 퍼미션 목록
    var PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION)

    //퍼미션 승인 요청시 사용하는 요청 코드
    var REQUEST_PERMISSION_CODE = 1

    //기본 맵 줌 레벨
    var DEFAULT_ZOOM_LEVEL = 17f

    //현재위치를 가져올수 없는 경우 - 461번지 위치
    //LatLng 클래스는 위도와 경도를 가지는 클래스
    var Default_loc = LatLng(35.796150, 128.494692);    //461번지 - 35.856944, 128.495984

    //구글 맵 객체를 참조할 멤버 변수
    var googleMap: GoogleMap? = null

    var mapView : MapView? = null

//    var fCameraPosition: CameraPosition? = null
//
//    var fGeoDataClient: GeoDataClient = null
//
//    var fPlaceDetectionClient: PlaceDetectionClient


    //---------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------


    override fun onStart() {
        super.onStart()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gpsViewModel =
            ViewModelProviders.of(this).get(GPSViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gps, container, false)
        val textView: TextView = root.findViewById(R.id.text_gps)
        gpsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        mapView = root.findViewById(R.id.googlemapView) as MapView

        mapView?.onCreate(savedInstanceState)
        if(hasPermissions()){
            //권한이 있는 경우 맵 초기화
            initMap()
        }
        else{
            //권한 요청
            activity?.let { ActivityCompat.requestPermissions(it, PERMISSIONS, REQUEST_PERMISSION_CODE) }
        }

//        var myLocationButton: FloatingActionButton? = activity?.findViewById(R.id.myLocationButton)
//        myLocationButton?.setOnClickListener { onMyLocationButtonClick() }


        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    //---------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------
    //Google Map

    //googleMap 권한요청 결과
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //맵 초기화
        initMap()
    }

    //---------------------------------------------------------------------------------------------------------------
    //앱에서 사용하는 권한이 있는지 체크하는 함수
    fun hasPermissions(): Boolean {

        //퍼미션 목록중 하나라도 권한이 없으면 false 반환
        for(permission in PERMISSIONS) {
            if(context?.let { ActivityCompat.checkSelfPermission(it, permission) } != PackageManager.PERMISSION_GRANTED) {

                return false
            }
        }

        return true
    }

    //---------------------------------------------------------------------------------------------------------------
    //맵 초기화하는 함수
    @SuppressLint("MissingPermission")
    fun initMap() {

        //맵뷰에서 구글 맵을 불러오는 함수, 컬렉함수에서 구글 맵 객체가 전달됨

        mapView?.getMapAsync {
            //구글맵 멤버 변수에 구글맵 객체 저장
            googleMap = it

//            var autocompleteFragment: Place? = activity.supportFragmentManager().find

            //현재 위치로 이동 버튼 비활성화
//            it.uiSettings.isMyLocationButtonEnabled = false

            googleMap?.addMarker(MarkerOptions().position(Default_loc).title("지우 집"))

            //위치 사용 권한이 있는 경우
            when {
                hasPermissions() -> {
                    //현재위치 표시 활성화
                    it.isMyLocationEnabled = true
                    //현재위치로 카메라 이동
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL))
                }
                else -> {
                    //권한이 없으면 지정위치(461번지)로 이동
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(Default_loc, DEFAULT_ZOOM_LEVEL))
                }
            }
        }
    }

    //---------------------------------------------------------------------------------------------------------------
    //내 위치 측정
    @SuppressLint("MissingPermission")
    fun getMyLocation(): LatLng {
        //위치를 측정하는 프로바이더를 GPS 센서로 지정
        val locationProvider: String = LocationManager.GPS_PROVIDER
        //위치 서비스 객체를 불러옴
        val locationManager= activity?.applicationContext?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //마지막으로 업데이트된 위치를 가져옴
        val lastKnownLocation: Location = locationManager?.getLastKnownLocation(locationProvider)

        //위도 경도 객체로 반환
        return LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
    }

    //---------------------------------------------------------------------------------------------------------------
    //현재 위치 버튼 클릭한 경우
    fun onMyLocationButtonClick() {
//        when {
//            hasPermissions() -> googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL))
//            else -> Toast.makeText(activity?.applicationContext, " 위치사용권한 설정에 동의해주세요", Toast.LENGTH_LONG).show()
//        }
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL))

    }

    //---------------------------------------------------------------------------------------------------------------
    //하단부터 맵뷰의 라이프사이클 함수 호출을 위한 코드들
    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override  fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    //---------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------
    //Google Map Places API - 내 주변 도서관, 서점
    var previous_maker:List<Marker>? = null

    override fun onPlacesFailure(e: PlacesException?) {

    }

    override fun onPlacesStart() {

    }

    override fun onPlacesSuccess(places: MutableList<Place>?) {
    }
    override fun onPlacesFinished() {

    }



}