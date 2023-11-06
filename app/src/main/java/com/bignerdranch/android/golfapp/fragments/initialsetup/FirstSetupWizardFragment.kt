package com.bignerdranch.android.golfapp.fragments.initialsetup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.golfapp.GolfViewModel
import com.bignerdranch.android.golfapp.R
import com.bignerdranch.android.golfapp.databinding.FragmentFirstSetupWizardBinding
import com.bignerdranch.android.golfapp.fragments.PopupFragment

class FirstSetupWizardFragment: Fragment() {

    private val golfViewModel: GolfViewModel by viewModels()
    private var _binding: FragmentFirstSetupWizardBinding? = null
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
            FragmentFirstSetupWizardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            yesButton.setOnClickListener {
                golfViewModel.registerBooleanAnswer(true)
                golfViewModel.moveToNext()
                updateQuestion()
            }
            noButton.setOnClickListener {
                golfViewModel.registerBooleanAnswer(false)
                golfViewModel.moveToNext()
                updateQuestion()
            }
            questionMarkButton.setOnClickListener {
                when (golfViewModel.currentIndex) {
                    0 -> setFairwayTip()
                    1 -> setGirTip()
                    2 -> setPenaltyTip()
                    3 -> setPutTip()
                    4 -> setPutTypeTip()
                    5 -> setPutTypeClassificationTip()
                }
            }
            backArrowIb.setOnClickListener {
                golfViewModel.incrementPutChoiceTracker()
                circlePutTypeClassification()
            }
            forwardArrowIb.setOnClickListener {
                golfViewModel.incrementPutChoiceTracker()
                circlePutTypeClassification()
            }
            pickButton.setOnClickListener {
                if (golfViewModel.putTypeChoiceTracker % 2 == 0) {
                    golfViewModel.registerStringAnswer(GolfViewModel.RANGE_CLASSIFIED_PUTS)
                } else {
                    golfViewModel.registerStringAnswer(GolfViewModel.CURVE_CLASSIFIED_PUTS)
                }
                golfViewModel.moveToNext()
                updateQuestion()
            }
        }
        updateQuestion()
    }

    private fun updateQuestion() {
        if (golfViewModel.currentIndex >= golfViewModel.getQuestionBankSize()) {
            navigateToStartMenu()
        } else {
            val questionText = golfViewModel.currentQuestionText
            binding.questionTextTv.text = questionText
            if (golfViewModel.currentIndex == 5) {
                handlePutTypeChoice()
                circlePutTypeClassification()
            } else if (golfViewModel.putTypeChoiceVisible && golfViewModel.currentIndex != 5) {
                clearPutTypeViews()
            }
        }
    }

    private fun navigateToStartMenu() {
        findNavController().navigate(FirstSetupWizardFragmentDirections.showStartMenu())
    }

    private fun handlePutTypeChoice() {
        binding.noButton.visibility = View.INVISIBLE
        binding.yesButton.visibility = View.INVISIBLE
        binding.noButton.isEnabled = false
        binding.yesButton.isEnabled = false
        binding.putTypeLl.visibility = View.VISIBLE
        binding.backArrowIb.visibility = View.VISIBLE
        binding.forwardArrowIb.visibility = View.VISIBLE
        binding.pickButton.visibility = View.VISIBLE
        binding.backArrowIb.isEnabled = true
        binding.forwardArrowIb.isEnabled = true
        binding.pickButton.isEnabled = true
    }

    private fun clearPutTypeViews() {
        binding.noButton.visibility = View.VISIBLE
        binding.yesButton.visibility = View.VISIBLE
        binding.noButton.isEnabled = true
        binding.yesButton.isEnabled = true
        binding.putTypeLl.visibility = View.INVISIBLE
        binding.backArrowIb.visibility = View.INVISIBLE
        binding.forwardArrowIb.visibility = View.INVISIBLE
        binding.pickButton.visibility = View.INVISIBLE
        binding.backArrowIb.isEnabled = false
        binding.forwardArrowIb.isEnabled = false
        binding.pickButton.isEnabled = false
    }

    private fun setFairwayTip() {
        val popupFragment = PopupFragment.newPopUp(
            getString(R.string.fairway_popup_title),
            getString(R.string.fairway_popup_description),
            R.drawable.fairway)
        popupFragment.show(childFragmentManager, "fairwayPopup")
    }

    private fun setGirTip() {
        val popupFragment = PopupFragment.newPopUp(
            getString(R.string.gir_popup_title),
            getString(R.string.gir_popup_description),
            R.drawable.green)
        popupFragment.show(childFragmentManager, "greenPopup")
    }

    private fun setPenaltyTip() {
        val popupFragment = PopupFragment.newPopUp(
            getString(R.string.penalties_popup_title),
            getString(R.string.penalties_popup_description),
            R.drawable.penalties)
        popupFragment.show(childFragmentManager, "penaltyPopup")
    }

    private fun setPutTip() {
        val popupFragment = PopupFragment.newPopUp(
            getString(R.string.put_popup_title),
            getString(R.string.put_popup_description),
            R.drawable.put)
        popupFragment.show(childFragmentManager, "putPopup")
    }

    private fun setPutTypeTip() {
        val popupFragment = PopupFragment.newPopUp(
            getString(R.string.put_type_popup_title),
            getString(R.string.put_type_popup_description),
            R.drawable.puttype)
        popupFragment.show(childFragmentManager, "putTypePopup")
    }

    private fun setPutTypeClassificationTip() {
        val popupFragment = PopupFragment.newPopUp(
            getString(R.string.put_type_choice_popup_title),
            getString(R.string.put_type_choice_popup_description),
            R.drawable.puttype)
        popupFragment.show(childFragmentManager, "putTypeClassificationPopup")
    }

    private fun circlePutTypeClassification() {
        golfViewModel.putTypeChoiceVisible = true
        if (golfViewModel.putTypeChoiceTracker % 2 == 0) {
            binding.putTypeSecondRow.text = getString(R.string.short_range_puts)
            binding.putTypeThirdRow.text = getString(R.string.medium_range_puts)
            binding.putTypeFourthRow.text = getString(R.string.long_range_puts)
        } else {
            binding.putTypeSecondRow.text = getString(R.string.drill_able_puts)
            binding.putTypeThirdRow.text = getString(R.string.drain_able_puts)
            binding.putTypeFourthRow.text = getString(R.string.lag_puts)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}