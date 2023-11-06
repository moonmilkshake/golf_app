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

class StartFragment: Fragment() {
    private val golfViewModel: GolfViewModel by viewModels()
    private var _binding: FragmentStartBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding is null and can't be accessed!"
        }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

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
        binding.apply {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}