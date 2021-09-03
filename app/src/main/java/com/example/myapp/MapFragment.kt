package com.example.myapp

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.yanzhenjie.permission.runtime.Permission.ACCESS_FINE_LOCATION
import noman.googleplaces.*

/*
class MapFragment : Fragment(),PlacesListener, OnMapReadyCallback {
    lateinit var loc: LatLng
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    var startupdate = false //위치정보 갱신을 start했는지의 여부
    lateinit var googleMap: GoogleMap
    lateinit var restaurantLoc: LatLng
    var clickitem = false
    lateinit var info: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        var root = inflater.inflate(R.layout.fragment_map2, container, false)
        //  if(arguments!=null){
        //리스트에서 클릭한 음식점의 주소 저장
        //    clickitem=true
        // restaurantLoc= arguments!!.get("location") as LatLng
        // info=ArrayList<String>(2)
        //  info.add(arguments!!.getString("name")!!)
        //  info.add(arguments!!.getString("tel")!!)
        // }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync {
            googleMap = it
        }
    }


    fun showResLos() {
        val option = MarkerOptions()
        //option.position(restaurantLoc)
        //option.title(info[0])
        //option.snippet(info[1])
        option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        googleMap.clear()
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLoc, 16.0f))
        googleMap.addMarker(option)!!.showInfoWindow()
        googleMap.addMarker(
            MarkerOptions()
                .position(loc)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        ) //현재 위치도 나오게
        stopLocationUpdate()
    }


    fun initLocation() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        locationRequest = LocationRequest.create().apply {
            interval = 15000
            fastestInterval = 10000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(location: LocationResult) {
                if (location.locations.size == 0) return
                //현재위치
                val latitude = location.locations[location.locations.size - 1].latitude
                val longitude = location.locations[location.locations.size - 1].longitude
                loc = LatLng(latitude, longitude)

                val konkuk = Location(LocationManager.GPS_PROVIDER)
                konkuk.latitude = 37.5408
                konkuk.longitude = 127.0793
                val currentLoc = Location(LocationManager.GPS_PROVIDER)
                currentLoc.latitude = latitude
                currentLoc.longitude = longitude
                val distance = currentLoc.distanceTo(konkuk)
                if (!clickitem) {
                    if (distance > 4000) {
                        setCurrentLocation(loc)
                    } else {
                        setCurrentLocation(loc)
                        initPlaces(loc)
                    }
                } else {
                    showResLos()
                }


            }
        }
    }

    private fun startLocationUpdates() {

        /*  if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        else{
            startupdate=true
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,locationCallback, Looper.getMainLooper()
            )
        }*/
    }

    //현재 위치로 위치 옮겨주는 함수
    fun setCurrentLocation(location: LatLng) {
        googleMap.clear()
        val option = MarkerOptions()
        option.position(location)
        option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        googleMap.addMarker(option)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16.0f))
    }

    private fun stopLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        startupdate = false
    }

    override fun onResume() {
        super.onResume()
        if (!startupdate)
            startLocationUpdates()

    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdate()
    }

    fun initPlaces(loc: LatLng) {
        if (!clickitem) {
            NRPlaces.Builder()
                .listener(this)
                .key("AIzaSyC7n8hJw_MJzP8GLsg00UP_sDXj8bx50aU")
                .latlng(loc.latitude, loc.longitude)
                .radius(700) //현재위치에서 700미터 이내 음식점
                .type(PlaceType.RESTAURANT)
                .build()
                .execute()
        }

    }


    override fun onPlacesFailure(e: PlacesException?) {

    }

    override fun onPlacesSuccess(places: MutableList<noman.googleplaces.Place>?) {
        activity?.runOnUiThread {
            if (places != null) {
                for (p in places) {
                    val nearLoc = LatLng(p.latitude, p.longitude)
                    val option = MarkerOptions()
                    option.position(nearLoc)
                    option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                    option.title(p.name)
                    googleMap.addMarker(option)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f))
                }
            }
        }

    }

    override fun onPlacesFinished() {

    }

    override fun onPlacesStart() {

    }

    override fun onMapReady(p0: GoogleMap?) {
        TODO("Not yet implemented")
        googleMap = p0!!

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


}

*/