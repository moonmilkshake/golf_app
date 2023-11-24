package com.bignerdranch.android.golfapp
import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
class PermissionHelper {
    fun startPermissionRequest(
        fragmentActivity: FragmentActivity, permissionInterface: PermissionInterface, manifest: String) {
        val requestPermissionLauncher =
            fragmentActivity.registerForActivityResult(ActivityResultContracts.RequestPermission(), permissionInterface::onGranted)
        requestPermissionLauncher.launch(
            manifest
        )
    }
}