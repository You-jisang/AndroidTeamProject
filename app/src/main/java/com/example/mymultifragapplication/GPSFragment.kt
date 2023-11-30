package com.example.mymultifragapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mymultifragapplication.databinding.FragmentGPSBinding
import com.example.mymultifragapplication.viewmodel.Locations
import com.example.mymultifragapplication.viewmodel.TodayLectureViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GPSFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentGPSBinding
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private val todayLectureViewModel: TodayLectureViewModel by activityViewModels()

    private val markers = mapOf(
        "강의동" to R.drawable.location_yellow,
        "과학관" to R.drawable.location_blue,
        "기계관" to R.drawable.location_green,
        "전자관" to R.drawable.location_red
    )

    private fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun updateMarkers() {
        if (!::googleMap.isInitialized) return

        todayLectureViewModel.lectures.value?.let { lectures ->
            googleMap.clear()

            lectures.forEach { lecture ->
                val location = Locations.locations[lecture.location]
                val marker = markers[lecture.location]
                if (location != null && marker != null) {
                    val originalBitmap = BitmapFactory.decodeResource(resources, marker)
                    val scaledBitmap = Bitmap.createScaledBitmap(
                        originalBitmap,
                        originalBitmap.width / 10,
                        originalBitmap.height / 10,
                        false
                    )
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(location)
                            .title(lecture.location)
                            .icon(BitmapDescriptorFactory.fromBitmap(scaledBitmap))
                    )
                }
            }
        }
    }

    @SuppressLint("VisibleForTests")
    private fun startLocationUpdates() {
        locationRequest = LocationRequest.create().apply {
            interval = 10000 // 업데이트 간격
            fastestInterval = 5000 // 가장 빠른 업데이트 간격
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY // 업데이트 우선순위
        }

        locationCallback = object : LocationCallback() {
            // 위치 정보 업데이트마다 호출
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    val userLocation = LatLng(location.latitude, location.longitude)
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            userLocation,
                            15.3f
                        )
                    )
                    // 거리 계산
                    val results = FloatArray(1)
                    val target = LatLng(37.60108, 126.8652)
                    Location.distanceBetween(
                        userLocation.latitude, userLocation.longitude,
                        target.latitude, target.longitude,
                        results
                    )
                    if (results[0] > 5000) {
                        // 항공대에서 5km 이상 떨어져 있을 때 Toast 메세지
                        Toast.makeText(context, "경고: 항공대에서 5km 이상 떨어져 있습니다.", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }

        if (checkLocationPermission()) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGPSBinding.inflate(inflater, container, false)
        mapView = binding.map3
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        if (checkLocationPermission()) {
            googleMap.isMyLocationEnabled = true // 현재 위치를 지도에 표시
            googleMap.uiSettings.isZoomControlsEnabled = true // 확대/축소 기능
        }
        updateMarkers()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        if (checkLocationPermission()) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        mapView.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
