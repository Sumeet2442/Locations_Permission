package com.example.locationpermission

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.locationpermission.databinding.FragmentFirstBinding
import pub.devrel.easypermissions.EasyPermissions

class FirstFragment : Fragment(),EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val permissions = arrayListOf<String>()
        permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        if(EasyPermissions.hasPermissions(requireContext(),permissions.toString())){
            gotoNext()
        }
        binding.btnPerm.setOnClickListener {
        EasyPermissions.requestPermissions(this,"Allow Permission", 12,permissions.toString())
        }
    }

    private fun gotoNext() {
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            this
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        gotoNext()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        AlertDialog.Builder(requireContext())
            .setTitle("PERMISSION REQUIRED!!")
            .setMessage("App won't run until the location permission is granted.")
            .setPositiveButton("Retry",DialogInterface.OnClickListener { dialogInterface, i ->  })
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}