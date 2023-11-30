package com.example.mymultifragapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymultifragapplication.databinding.FragmentTomorrowMapBinding
import com.example.mymultifragapplication.viewmodel.DateViewModel
import com.example.mymultifragapplication.viewmodel.Lecture
import com.example.mymultifragapplication.viewmodel.Locations
import com.example.mymultifragapplication.viewmodel.TomorrowViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class TomorrowMapFragment : Fragment(), OnMapReadyCallback {

    private var binding: FragmentTomorrowMapBinding? = null
    private lateinit var mapView: MapView
    private lateinit var dateText: TextView
    private lateinit var todayButton: Button
    private lateinit var googleMap: GoogleMap

    private val viewModel: DateViewModel by activityViewModels()
    private val tomorrowViewModel: TomorrowViewModel by activityViewModels()


    private val markers = mapOf(
        "강의동" to R.drawable.location_yellow,
        "과학관" to R.drawable.location_blue,
        "기계관" to R.drawable.location_green,
        "전자관" to R.drawable.location_red
    )

    private fun updateMarkers(lectures: List<Lecture>) {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTomorrowMapBinding.inflate(inflater, container, false)
        mapView = binding?.map2!!
        dateText = binding?.tomorrowText!!
        todayButton = binding?.buttonTomorrow!!
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)


        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tomorrowDate.observe(viewLifecycleOwner) {
            binding?.tomorrowText?.text = viewModel.tomorrowDate.value
        }

        binding?.buttonTomorrow?.setOnClickListener {
            findNavController().navigate(R.id.action_tomorrowMapFragment_to_mapFragment)
        }

        val lectureAdapter = LectureAdapter(emptyList())
        binding?.tomorrowList?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = lectureAdapter
        }

        tomorrowViewModel.lectures.observe(viewLifecycleOwner) { lectures ->
            binding?.tomorrowList?.adapter = LectureAdapter(lectures)
            if (::googleMap.isInitialized) {
                updateMarkers(lectures)
            }
        }
    }


    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val target = LatLng(37.60108, 126.8652) // 항공대의 위도와 경도
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(target, 17.2f)
        ) // 카메라를 타겟으로 이동하고, 줌 레벨을 설정
        tomorrowViewModel.lectures.value?.let { updateMarkers(it) }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        if (::googleMap.isInitialized) {
            tomorrowViewModel.lectures.value?.let { updateMarkers(it) }
        }
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
