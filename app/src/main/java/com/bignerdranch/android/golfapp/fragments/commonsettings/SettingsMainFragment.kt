package com.bignerdranch.android.golfapp.fragments.commonsettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bignerdranch.android.golfapp.GolfViewModel
import com.bignerdranch.android.golfapp.R
import com.bignerdranch.android.golfapp.databinding.FragmentSettingsMainBinding
import com.bignerdranch.android.golfapp.fragments.PopupFragment

class SettingsMainFragment: Fragment() {
    private val golfViewModel: GolfViewModel by viewModels()
    private var _binding: FragmentSettingsMainBinding? = null
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
            FragmentSettingsMainBinding.inflate(layoutInflater, container, false)
        reloadSettings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            fairwayTrackingCb.setOnCheckedChangeListener { _, isChecked ->
                setFairwayTracking(isChecked)
            }
            girTrackingCb.setOnCheckedChangeListener { _, isChecked ->
                setGirTracking(isChecked)
            }
            penaltyTrackingCb.setOnCheckedChangeListener { _, isChecked ->
                setPenaltyTracking(isChecked)
            }
            putTrackingCb.setOnCheckedChangeListener { _, isChecked ->
                setPutTracking(isChecked)
            }
            putTypeTrackingCb.setOnCheckedChangeListener { _, isChecked ->
                setPutTypeTracking(isChecked)
            }
            backArrowSettingIb.setOnClickListener {
                golfViewModel.incrementPutChoiceTracker()
                circlePutTypeClassification()
            }
            forwardArrowSettingIb.setOnClickListener {
                golfViewModel.incrementPutChoiceTracker()
                circlePutTypeClassification()
            }
        }
    }

    private fun setFairwayTracking(isChecked: Boolean) {
        if (isChecked) {
            golfViewModel.setFairwayTracking(true)
        } else {
            golfViewModel.setFairwayTracking(false)
        }
    }

    private fun setGirTracking(isChecked: Boolean) {
        if (isChecked) {
            golfViewModel.setGirTracking(true)
        } else {
            golfViewModel.setGirTracking(false)
        }
    }

    private fun setPenaltyTracking(isChecked: Boolean) {
        if (isChecked) {
            golfViewModel.setPenaltyTracking(true)
        } else {
            golfViewModel.setPenaltyTracking(false)
        }
    }

    private fun setPutTracking(isChecked: Boolean) {
        if (isChecked) {
            golfViewModel.setPutTracking(true)
        } else {
            binding.putTypeTrackingCb.isChecked = false
            golfViewModel.setPutTracking(false)
            setPutTypeTracking(false)
        }
    }

    private fun setPutTypeTracking(isChecked: Boolean) {
        if (isChecked) {
            binding.putTrackingCb.isChecked = true
            golfViewModel.setPutTypeTracking(true)
            setPutTracking(true)
            showPutTypeClassifications()
            circlePutTypeClassification()
        } else {
            golfViewModel.setPutTypeTracking(false)
            golfViewModel.setPutTypeClassification(GolfViewModel.PUT_CLASSIFICATION_OFF)
            hidePutTypeClassifications()
        }
    }

    private fun showPutTypeClassifications() {
        binding.putClassificationSettingsLl.visibility = View.VISIBLE
        binding.backArrowSettingIb.visibility = View.VISIBLE
        binding.forwardArrowSettingIb.visibility = View.VISIBLE
        binding.backArrowSettingIb.isEnabled = true
        binding.forwardArrowSettingIb.isEnabled = true
    }

    private fun hidePutTypeClassifications() {
        binding.putClassificationSettingsLl.visibility = View.INVISIBLE
        binding.backArrowSettingIb.visibility = View.INVISIBLE
        binding.forwardArrowSettingIb.visibility = View.INVISIBLE
        binding.backArrowSettingIb.isEnabled = false
        binding.forwardArrowSettingIb.isEnabled = false
    }

    private fun circlePutTypeClassification() {
        if (golfViewModel.putTypeChoiceTracker % 2 == 0) {
            binding.putTypeSettingSecondRow.text = getString(R.string.short_range_puts)
            binding.putTypeSettingThirdRow.text = getString(R.string.medium_range_puts)
            binding.putTypeSettingFourthRow.text = getString(R.string.long_range_puts)
            golfViewModel.setPutTypeClassification(GolfViewModel.RANGE_CLASSIFIED_PUTS)
        } else {
            binding.putTypeSettingSecondRow.text = getString(R.string.drill_able_puts)
            binding.putTypeSettingThirdRow.text = getString(R.string.drain_able_puts)
            binding.putTypeSettingFourthRow.text = getString(R.string.lag_puts)
            golfViewModel.setPutTypeClassification(GolfViewModel.CURVE_CLASSIFIED_PUTS)
        }
    }

    private fun reloadSettings() {
        binding.fairwayTrackingCb.isChecked = golfViewModel.getFairwayTracking()
        binding.girTrackingCb.isChecked = golfViewModel.getGirTracking()
        binding.penaltyTrackingCb.isChecked = golfViewModel.getPenaltyTracking()
        binding.putTrackingCb.isChecked = golfViewModel.getPutTracking()
        binding.putTypeTrackingCb.isChecked = golfViewModel.getPutTypeTracking()
        if (golfViewModel.getPutTypeTracking()) {
            showPutTypeClassifications()
            golfViewModel.putTypeChoiceTracker =
                if (golfViewModel.getPutTypeClassification().equals(GolfViewModel.RANGE_CLASSIFIED_PUTS)) {
                0
            } else {
                1
            }
            circlePutTypeClassification()
        } else {
            hidePutTypeClassifications()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}