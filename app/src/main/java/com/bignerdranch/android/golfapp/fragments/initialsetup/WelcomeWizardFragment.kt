package com.bignerdranch.android.golfapp.fragments.initialsetup

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.golfapp.R
import com.bignerdranch.android.golfapp.databinding.FragmentWelcomeWizardBinding
const val THREE_SECOND_DELAY = 3000L
const val FOUR_SECOND_DELAY = 4000L
const val FIVE_SECOND_DELAY = 5000L
const val SIX_SECOND_DELAY = 6000L
const val SEVEN_SECOND_DELAY = 7000L
const val EIGHT_SECOND_DELAY = 8000L
const val NINE_SECOND_DELAY = 9000L
const val TEN_SECOND_DELAY = 10000L

class WelcomeWizardFragment: Fragment() {
    private var _binding: FragmentWelcomeWizardBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding is null and can't be accessed!"
        }
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var fadeInAnimation: Animation
    private lateinit var fadeOutAnimation: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentWelcomeWizardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        fadeOutAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)

        startWelcomeText()

        binding.apply {
            welcomeRl.setOnClickListener{
                findNavController().navigate(
                    WelcomeWizardFragmentDirections.showFirstSetupWizard()
                )
            }
        }
    }

    private fun startWelcomeText() {
        fadeInAndOutText(getString(R.string.welcome_text), THREE_SECOND_DELAY)  //5 sekunder
        handler.postDelayed({
            fadeInAndOutText(getString(R.string.tracking_info_part_one), FIVE_SECOND_DELAY) //7 sekunder
            handler.postDelayed({
                fadeInAndOutText(getString(R.string.tracking_info_part_two), FIVE_SECOND_DELAY) //7 sekunder
                handler.postDelayed({
                    fadeInAndOutText(getString(R.string.set_preferences), FOUR_SECOND_DELAY) //6 sekunder
                    handler.postDelayed({
                        findNavController().navigate(WelcomeWizardFragmentDirections.showFirstSetupWizard())
                    }, SIX_SECOND_DELAY)
                }, SEVEN_SECOND_DELAY)
            }, SEVEN_SECOND_DELAY)
        }, FIVE_SECOND_DELAY)
    }


    private fun fadeInAndOutText(
        text: String,
        delayMillis: Long
    ) {
        binding.fadingWelcomeWizardText.text = text
        binding.fadingWelcomeWizardText.startAnimation(fadeInAnimation)
        binding.fadingWelcomeWizardText.visibility = View.VISIBLE
        handler.postDelayed({
            binding.fadingWelcomeWizardText.visibility = View.INVISIBLE
            binding.fadingWelcomeWizardText.startAnimation(fadeOutAnimation)
        }, delayMillis)
    }

//    private fun fadeInAndOutText(
//        view: View,
//        delayMillis: Long
//    ) {
//        view.startAnimation(fadeInAnimation)
//        view.visibility = View.VISIBLE
//        handler.postDelayed({
//            view.visibility = View.INVISIBLE
//            view.startAnimation(fadeOutAnimation)
//        }, delayMillis)
//    }


    override fun onDestroy() {
        super.onDestroy()
        // Remove any pending callbacks to avoid memory leaks
        handler.removeCallbacksAndMessages(null)
        _binding = null
    }

}
