package com.example.mymultifragapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymultifragapplication.databinding.FragmentMapBinding
import com.example.mymultifragapplication.viewmodel.DateViewModel
import com.example.mymultifragapplication.viewmodel.TodayLectureViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng


class MapFragment : Fragment(), OnMapReadyCallback {

    private var binding: FragmentMapBinding? = null
    private lateinit var mapView: MapView
    private lateinit var dateText: TextView
    private lateinit var refreshButton: Button

    private val viewModel: DateViewModel by activityViewModels()
    private val todayLectureViewModel: TodayLectureViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        mapView = binding?.map1!!
        dateText = binding?.todayText!!
        refreshButton = binding?.buttonToday!!
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)


        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.date.observe(viewLifecycleOwner) {
            binding?.todayText?.text = viewModel.date.value
        }
        binding?.buttonToday?.setOnClickListener {
            findNavController().navigate(R.id.action_mapFragment_to_tomorrowMapFragment)
        }

        binding?.naviBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_mapFragment_to_GPSFragment)
        }

        val lectureAdapter = LectureAdapter(emptyList())
        binding?.todayList?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = lectureAdapter
        }

        todayLectureViewModel.lectures.observe(viewLifecycleOwner) { lectures ->
            binding?.todayList?.adapter = LectureAdapter(lectures)
        }


    }

    override fun onMapReady(googleMap: com.google.android.gms.maps.GoogleMap) {
        val seoul = LatLng(37.60108, 126.8652) // 항공대의 위도와 경도
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 17.8f)) // 카메라를 서울로 이동하고, 줌 레벨을 10으로 설정
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
