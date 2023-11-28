package com.example.mymultifragapplication

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.mymultifragapplication.databinding.FragmentGPSBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class GPSFragment : Fragment(), OnMapReadyCallback {

    private var binding: FragmentGPSBinding? = null
    private lateinit var mapView: MapView
    private lateinit var myLocation: ImageView
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGPSBinding.inflate(inflater, container, false)
        mapView = binding?.map3!!
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        myLocation = binding?.myLocation!!

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        myLocation.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request location permissions
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1000
                )
            } else {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        location?.let {
                            if (::googleMap.isInitialized) {
                                val userLocation = LatLng(it.latitude, it.longitude)
                                val markerIcon =
                                    BitmapDescriptorFactory.fromResource(R.drawable.location_yellow)
                                googleMap.addMarker(
                                    MarkerOptions().position(userLocation).icon(markerIcon)
                                )
                                googleMap.moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        userLocation,
                                        17.3f
                                    )
                                )
                            }
                        }
                    }
            }
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onMapReady(googleMap: com.google.android.gms.maps.GoogleMap) {
        val seoul = LatLng(37.60108, 126.8652) // 항공대의 위도와 경도
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                seoul,
                17.3f
            )
        ) // 카메라를 서울로 이동하고, 줌 레벨을 10으로 설정
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
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