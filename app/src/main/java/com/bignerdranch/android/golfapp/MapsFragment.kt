package com.bignerdranch.android.golfapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bignerdranch.android.golfapp.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment: Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMapsBinding? = null
    private lateinit var golfMap: GoogleMap
    private val binding
        get() = checkNotNull(_binding) {
            "Binding is null and can't be accessed!"
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentMapsBinding.inflate(layoutInflater, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return binding.root
    }

    @Override
    override fun onMapReady(googleMap: GoogleMap) {
        golfMap = googleMap
        golfMap.mapType = GoogleMap.MAP_TYPE_SATELLITE

        val parkCord = LatLng(59.308, 17.994)
        golfMap.addMarker(MarkerOptions().position(parkCord).title("BoboPark"))

        val cameraPosition = CameraPosition.Builder().target(LatLng(59.308, 17.994)).zoom(15.0f).build()
        golfMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}