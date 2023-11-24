package com.bignerdranch.android.golfapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.golfapp.databinding.FragmentStartBinding
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.bignerdranch.android.golfapp.StartFragmentDirections
import com.bignerdranch.android.golfapp.fragments.PopupFragment


private const val RC_LOCATION_PERMISSION = 123

class StartFragment: Fragment() {
    private val golfViewModel: GolfViewModel by viewModels()
    private var _binding: FragmentStartBinding? = null
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
            FragmentStartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //Implement functionality
        super.onViewCreated(view, savedInstanceState)
        requestLocationPermission()
        binding.apply {
            startRound.setOnClickListener {
                findNavController().navigate(
                    StartFragmentDirections.showStartRound())
            }
            tempTestWelcome.setOnClickListener {
                findNavController().navigate(
                    StartFragmentDirections.showWelcomeWizard()
                )
            }
            commonSettings.settingsButton.setOnClickListener {
                findNavController().navigate(
                    StartFragmentDirections.showCommonSettings()
                )
            }
        }

        if (golfViewModel.getIsFirstLaunch()) {
            findNavController().navigate(
                StartFragmentDirections.showWelcomeWizard()
            )
            golfViewModel.setIsFirstLaunch(false)
        }
    }

    private fun requestLocationPermission() {
        val fragmentActivity: FragmentActivity? = activity
        if (fragmentActivity != null) {
            PermissionHelper().startPermissionRequest(fragmentActivity,
                object : PermissionInterface {
                    override fun onGranted(isGranted: Boolean) {
                        if (isGranted) {
                            // Permission is granted. Continue the action or workflow in your
                            // app.
                        } else {
                            // Explain the need to the user
                            val popupFragment = PopupFragment.newPopUp(
                                getString(R.string.fairway_popup_title),
                                getString(R.string.fairway_popup_description),
                                R.drawable.fairway)
                            popupFragment.show(childFragmentManager, "fairwayPopup")
                        }
                    }
                }, Manifest.permission.ACCESS_FINE_LOCATION )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}