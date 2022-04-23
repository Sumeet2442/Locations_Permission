package com.example.locationpermission

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.example.locationpermission.databinding.FragmentSecondBinding
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        return binding.root

    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createLocationRequest()
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(loc: LocationResult) {
                loc ?: return
                for (location in loc.locations){
                    binding.txtLoc.text = "Coordinates: ${location.latitude}, ${location.longitude}"
                    binding.txtAlt.text = "Altitude: ${location.altitude} \nSpeed: ${location.speed}"
                    binding.txtDimLoc1.text = "Coordinates: ${location.latitude},}"
                    binding.txtDimLoc2.text = "Altitude: ${location.latitude}}"
                }
            }
        }
    }
//        FOR LAST KNOWN LOCATION
//        fusedLocationClient.lastLocation.addOnSuccessListener {
//            binding.txtLoc.text = "Coordinates: ${it.latitude}, ${it.longitude}"
//            binding.txtAlt.text = "Altitude: ${it.altitude} \nSpeed: ${it.speed}"
//            binding.txtDimLoc1.text = "Coordinates: ${it.latitude}, ${it.longitude}"
//            binding.txtDimLoc2.text = "Altitude: ${it.latitude} \nSpeed: ${it.longitude}"
//        }.addOnFailureListener {
//        val error = it.message.toString()
//            Snackbar.make(binding.root,error,Snackbar.LENGTH_LONG).show()
//        }

    fun createLocationRequest() {
         locationRequest = LocationRequest.create().apply {
            interval = 1000
            fastestInterval = 500
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}