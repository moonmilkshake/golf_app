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

        fadeInAndOutText(binding.fadingWelcomeText, THREE_SECOND_DELAY)

        handler.postDelayed({
            fadeInAndOutText(binding.fadingPreferencesText, FOUR_SECOND_DELAY)
//            handler.postDelayed({
//                findNavController().navigate(WelcomeWizardFragmentDirections.showFirstSetupWizard())
//            }, SIX_SECOND_DELAY)
//            TODO: Enable when wizard is finished otherwise it'll crash when clicked past
        }, FIVE_SECOND_DELAY)

        binding.apply {
            welcomeRl.setOnClickListener{
                findNavController().navigate(
                    WelcomeWizardFragmentDirections.showFirstSetupWizard()
                )
            }
        }
    }

    private fun fadeInAndOutText(
        view: View,
        delayMillis: Long
    ) {
        view.startAnimation(fadeInAnimation)
        view.visibility = View.VISIBLE
        handler.postDelayed({
            view.visibility = View.INVISIBLE
            view.startAnimation(fadeOutAnimation)
        }, delayMillis)
    }


    override fun onDestroy() {
        super.onDestroy()
        // Remove any pending callbacks to avoid memory leaks
        handler.removeCallbacksAndMessages(null)
        _binding = null
    }

}
