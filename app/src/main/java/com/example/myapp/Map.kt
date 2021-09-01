package com.example.myapp

import android.app.Activity
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.PlaceDetectionClient
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Map : Fragment(), OnMapReadyCallback {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var map: GoogleMap
    private lateinit var locationClient: FusedLocationProviderClient

    //Place API에 접근해 현재위치를 얻을 변수
    private lateinit var placeDetectionClient: PlaceDetectionClient

    //places API에 접근해 지역정보를 얻을 변수
    private lateinit var geoDataClient: GeoDataClient

    //위치 정보 사용을 위한 변수
    private var permissionGranted: Boolean = false
    private val PERMISSION_ACCESS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("fragment", "ddddd")

        if(!permissionGranted) {
            AndPermission.with(this).runtime().permission(Permission.Group.LOCATION)
                .onGranted { permissions ->
                    Log.d("permissions", "허용된 권한: ${permissions.size}")
                }
                .onDenied { permissions ->
                    Log.d("permissions", "거부된 권한: ${permissions.size}")
                }.start()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_ACCESS -> {
                if (grantResults.size > 0 && grantResults.get(0) == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("fragment", "onCreateView실행")
        val view: View = inflater.inflate(R.layout.fragment_map, container, false)

        //btn=view.findViewById<ImageButton>(R.id.my_trace)


        val mapFragment =
            childFragmentManager.findFragmentById(R.id.sanitarypad_map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        return view
    }

    //onCreateView에서 getMapAsynce()호출 시 onMapReady()호출됨->맵에서 사용할 코드 작성
    override fun onMapReady(p0: GoogleMap) {
        Log.d("fragment", "onMapReady실행")
        map = p0

        //위치 권한 허용되어있는지 다시 확인->map.isMyLocationEnabled=true사용하려면 체크해줘야함
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            permissionGranted = true
        } else {
            //허용 안돼있으면 requestPermissions()호출; 여기서 자동으로 onRequestPermissionsResult()호출함
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_ACCESS
            )
        }

        locationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        geoDataClient = Places.getGeoDataClient(requireActivity())
        placeDetectionClient = Places.getPlaceDetectionClient(requireActivity())

        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.uiSettings.isZoomControlsEnabled = true

        map.isMyLocationEnabled = true

        var retrofit = Retrofit.Builder().baseUrl(ApiService.API_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build()
        var apiService = retrofit.create(ApiService::class.java)

        var emergency = apiService.get_mapList("json")

        try {
            if (permissionGranted) {
                locationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        val lat = location!!.latitude
                        val lng = location.longitude

                        //이미지 필요없이 옵션 사용해서 마커 추가
                        map.addMarker(
                            MarkerOptions().position(LatLng(lat, lng)).title("현재 내 위치")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        )

                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 15f))
                    }
            } else {
                Log.d("curr location", "현재 위치 추적 불가")
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
            Log.d("curr location", e.message.toString())
        }

        emergency.enqueue(object : Callback<List<mapInfo>> {
            override fun onResponse(call: Call<List<mapInfo>>, response: Response<List<mapInfo>>) {
                if (response.isSuccessful) {
                    var emergencyPadList = response.body()!!

                    for (l in emergencyPadList) {
                        map.addMarker(
                            MarkerOptions().position(
                                LatLng(
                                    l.latitude!!.toDouble(),
                                    l.longitude!!.toDouble()
                                )
                            )
                                .title(l.name)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<List<mapInfo>>, t: Throwable) {
                Log.d("emergency map get", t.message.toString())
            }
        })
        Log.d("fragment", "오류7")
    }

//
//    private fun requestLocation() {
//        Log.d("req_location", "위치 변화 감지")
//        try {
//            locationClient?.lastLocation?.addOnSuccessListener { location: Location ->
//                if (location == null) {
//                    Log.d("location_check", "최근 위치 추적 실패")
//                } else {
//                    Log.d(
//                        "location_check",
//                        "최근 위치 추적 성공: ${location.latitude},${location.longitude}"
//                    )
//                }
//            }
//                ?.addOnFailureListener {
//                    Log.d("location_check", "최근 위치 추적 실패-리스너 실행x")
//                    it.printStackTrace()
//                }
//
//            val curr_location_request = LocationRequest.create()
//            curr_location_request.run {
//                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//                interval = 60 * 1000
//            }
//            val curr_location_callback = object : LocationCallback() {
//                override fun onLocationResult(p0: LocationResult?) {
//                    p0?.let {
//                        for ((i, location) in it.locations.withIndex()) {
//                            Log.d("my_location", "내 위치:${location.latitude},${location.longitude}")
//                        }
//                        val curPoint = LatLng(it.locations[0].latitude, it.locations[0].longitude)
//                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15f))
//                        map.addMarker(
//                            MarkerOptions().position(
//                                LatLng(
//                                    it.locations[0].latitude,
//                                    it.locations[0].longitude
//                                )
//                            )
//                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
//                        )
//                    }
//                }
//            }
//            locationClient?.requestLocationUpdates(
//                curr_location_request,
//                curr_location_callback,
//                Looper.myLooper()
//            )
//        } catch (e: SecurityException) {
//            e.printStackTrace()
//        }
//    }

}