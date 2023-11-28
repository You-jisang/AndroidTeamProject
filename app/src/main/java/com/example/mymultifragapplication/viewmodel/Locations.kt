package com.example.mymultifragapplication.viewmodel

import com.google.android.gms.maps.model.LatLng

class Locations {
    companion object {
        val locations = mapOf(
            "강의동" to LatLng(37.6001, 126.8666),
            "과학관" to LatLng(37.6015, 126.8650),
            "기계관" to LatLng(37.6013, 126.8645),
            "전자관" to LatLng(37.6007, 126.8647)
        )
    }
}