package com.example.mymultifragapplication

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
import com.example.mymultifragapplication.viewmodel.TomorrowViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng


class TomorrowMapFragment : Fragment(), OnMapReadyCallback {

    private var binding: FragmentTomorrowMapBinding? = null
    private lateinit var mapView: MapView
    private lateinit var dateText: TextView
    private lateinit var TodayButton: Button

    private val viewModel: DateViewModel by activityViewModels()
    private val tomorrowViewModel: TomorrowViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTomorrowMapBinding.inflate(inflater, container, false)
        mapView = binding?.map2!!
        dateText = binding?.tomorrowText!!
        TodayButton = binding?.buttonTomorrow!!
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
        }
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
