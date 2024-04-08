package com.example.capstoneapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.util.Locale

class MapViewActivity : FragmentActivity(), OnMapReadyCallback {

    companion object {
        private const val TAG = "TAG내용"
        private const val GPS_ENABLE_REQUEST_CODE = 2001
        private const val UPDATE_INTERVAL_MS = 600000 // 10분
        private const val FASTEST_UPDATE_INTERVAL_MS = 20000 // 20초
        private const val PERMISSIONS_REQUEST_CODE = 2000
    }

    private var needRequest = false
    private  var mMap: GoogleMap? = null
    private lateinit var mLayout: View
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var location: Location
    private var currentMarker: Marker? = null

    private val REQUIRED_PERMISSIONS =
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET)

    private var mCurrentLocation: Location? = null
    private var currentPosition: LatLng? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mLayout = findViewById(R.id.map_view)
        Log.d(TAG, "onCreate")

        locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL_MS.toLong())
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS.toLong())

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            p0.let {
                val locationList = it.locations
                if (locationList.isNotEmpty()) {
                    location = locationList[locationList.size - 1]
                    Log.d("Location",location.latitude.toString())
                    Log.d("Location",location.longitude.toString())

                    currentPosition = LatLng(location.latitude, location.longitude)
                    Log.d(TAG,location.latitude.toString()+" "+location.longitude)
                    val markerTitle = getCurrentAddress(currentPosition!!)
                    val markerSnippet =
                        "위도:${location.latitude} 경도:${location.longitude}"
                    setCurrentLocation(location, markerTitle, markerSnippet)
                    mCurrentLocation = location
                }
            }
        }
    }

    private fun startLocationUpdates() {
        if (!checkLocationServicesStatus()) {
            Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting")
            showDialogForLocationServiceSetting()
        } else {
            val hasFineLocationPermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            val hasCoarseLocationPermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(TAG, "startLocationUpdates : 퍼미션 안가지고 있음")
                return
            }
            Log.d(TAG, "startLocationUpdates : call mFusedLocationClient.requestLocationUpdates")
            mFusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )
            if (checkPermission()) {
                mMap?.isMyLocationEnabled = true
            }
        }
    }

    /*
    * 구글 지도가 준비되었을 때 호출되는 onMapReady() 메서드 재정의
    * 구글 지도가 준비되면 초기화 작업 수행, 위치 권한 있는지 확인
    * 위치 업데이트를 시작하거나 사용자에게 권한 요청을 처리하는 등 작업 수행
    * */
    override fun onMapReady(googleMap: GoogleMap) {
        Log.d(TAG, "onMapReady :")
        mMap = googleMap
        setDefaultLocation()
        val hasFineLocationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarseLocationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
            hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
        ) {
            startLocationUpdates()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    REQUIRED_PERMISSIONS[0]
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    REQUIRED_PERMISSIONS[1]
                )
            ) {
                Snackbar.make(
                    mLayout,
                    "이 앱을 실행하려면 위치 접근 권한이 필요합니다.",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("확인") {
                    ActivityCompat.requestPermissions(
                        this@MapViewActivity,
                        REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE
                    )
                }.show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            }
        }
        mMap?.uiSettings?.isMyLocationButtonEnabled = true
        mMap?.animateCamera(CameraUpdateFactory.zoomTo(15f))
        mMap?.setOnMapClickListener { latLng -> Log.d(TAG, "onMapClick :") }
    }

    /*
    * 권한 요청 결과 처리
    * */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.size == REQUIRED_PERMISSIONS.size) {
            var checkResult = true
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    checkResult = false
                    break
                }
            }
            if (checkResult) {
                startLocationUpdates()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        REQUIRED_PERMISSIONS[0]
                    ) || ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        REQUIRED_PERMISSIONS[1]
                    )
                ) {
                    Snackbar.make(
                        mLayout,
                        "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요. ",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("확인") {
                        finish()
                    }.show()
                } else {
                    Snackbar.make(
                        mLayout,
                        "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("확인") {
                        finish()
                    }.show()
                }
            }
        }
    }

    /*
    * 위치 서비스 상태 확인
    * 앱에서 위치서비스를 사용하기 전 위치서비스가 활성화되어 있는지 확인
    * */
    private fun checkLocationServicesStatus(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        //GPS 제공자, 네트워크 제공자 중 하나 이상이 활성화 되었을 경우 true 반환
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    /*
    * 주어진 위도, 경도에 해당되는 주소를 가져옴
    * 해당 위치의 주소를 보여주는데 사용할 수 있음
    * */
    private fun getCurrentAddress(latlng: LatLng): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: MutableList<Address>? =
                geocoder.getFromLocation(latlng.latitude,latlng.longitude,7)
            if (addresses.isNullOrEmpty()) {
                return "주소 미발견"
            } else {
                val address = addresses[0]
                return address.getAddressLine(0)
            }
        } catch (ioException: IOException) {
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show()
            return "지오코더 서비스 사용불가"
        } catch (illegalArgumentException: IllegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show()
            return "잘못된 GPS 좌표"
        }
    }

    /*
    * 현재위치 기반으로 지도에 마커 설정, 해당 마커와 함께 지도 조정
    * 위치서비스를 사용하여 사용자의 현재 위치를 찾고 그 위치에 대한 마커를 지도에 표시
    * */
    private fun setCurrentLocation(location: Location, markerTitle: String, markerSnippet: String) {
        currentMarker?.remove()
        val currentLatLng = LatLng(location.latitude,location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(currentLatLng)
        markerOptions.title(markerTitle)
        markerOptions.snippet(markerSnippet)
        markerOptions.draggable(true)
        currentMarker = mMap?.addMarker(markerOptions)
        val cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng)
        mMap?.moveCamera(cameraUpdate)
        mMap?.animateCamera(CameraUpdateFactory.zoomTo(17.0f))
    }

    private fun setDefaultLocation() {
        //디폴트 위치, Seoul
        val DEFAULT_LOCATION = LatLng(37.5562, 126.9724)
        val markerTitle = "위치정보 가져올 수 없음"
        val markerSnippet = "위치 퍼미션과 GPS 활성 요부 확인하세요"
        currentMarker?.remove()
        val markerOptions = MarkerOptions()
        markerOptions.position(DEFAULT_LOCATION)
        markerOptions.title(markerTitle)
        markerOptions.snippet(markerSnippet)
        markerOptions.draggable(true)
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        currentMarker = mMap?.addMarker(markerOptions)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15f)
        mMap?.moveCamera(cameraUpdate)
    }

    /*
    * 위치 서비스가 비활성되어 있을 때 사용자에게 위치 서비스 활성화 여부를 묻는 대화상자 보여줌
    * 사용자 응답에 따라 동작 수행
    * */
    private fun showDialogForLocationServiceSetting() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage("앱을 사용하기 위해 위치 서비스가 필요합니다.\n" +
                "위치 설정을 수정하실래요?")
        builder.setCancelable(true)
        /*
        * 활성화할 경우 설정화면으로 이동(Intent 시작)
        * ACTION_LOCATION_SOURCE_SETTINGS 액션 사용해 위치 설정 화면 염
        * startActivityForResult() 사용해 액티비티 시작.
        * 결과를 받기 위해 GPS_ENABLE_REQUEST_CODE로 요청코드 설정
        * */
        builder.setPositiveButton("설정") { _, _ ->
            val callGPSSettingIntent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE)
        }
        builder.setNegativeButton("취소") { dialog, _ -> dialog.cancel() }
        builder.create().show()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
        if (checkPermission()) {
            Log.d(TAG, "onStart : call mFusedLocationClient.requestLocationUpdates")
            mFusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
            if (mMap != null) mMap?.isMyLocationEnabled = true
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
        mFusedLocationClient.removeLocationUpdates(locationCallback)
    }

    /*
    * 사용자의 행동에 따라 실행되는 결과 처리
    * startActivityF
    * */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //상위 클래스의 onActivityResult 호출. 부모 클래스의 정의된 기본 동작 유지를 위해 필요함
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GPS_ENABLE_REQUEST_CODE -> {
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d(TAG, "onActivityResult : GPS 활성화 되있음")
                        needRequest = true
                        return
                    }
                }
            }
        }
    }

    /*
    * 위치 권한이 부여되었는지 확인
    * 위치 기반 기능을 사용하기 전 위치 권한이 부여되었는지 확인할 때 사용
    * 권한 부여가 되지 않았다면 사용자에게 권한 요청
    * */
    private fun checkPermission(): Boolean {
        val hasFineLocationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarseLocationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        return hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
    }
}

//override fun onMapReady(googleMap: GoogleMap) {
//    // Default location (San Francisco)
//    val defaultLocation = LatLng(37.7749, -122.4194)
//    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))
//
//    // Retrieve user's location from Firebase
//    val userName = intent.getStringExtra("userName")
//    userName?.let { name ->
//        database.child(name).addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val latitude = snapshot.child("latitude").value as? Double
//                val longitude = snapshot.child("longitude").value as? Double
//                if (latitude != null && longitude != null) {
//                    val userLocation = LatLng(latitude, longitude)
//                    googleMap.addMarker(MarkerOptions().position(userLocation).title(name))
//                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
//                } else {
//                    Toast.makeText(applicationContext, "User's location not found", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(applicationContext, "Failed to retrieve user's location", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//}
//}