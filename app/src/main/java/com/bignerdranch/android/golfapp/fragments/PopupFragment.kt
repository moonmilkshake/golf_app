package com.bignerdranch.android.golfapp.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bignerdranch.android.golfapp.databinding.FragmentPopupBinding

class PopupFragment : DialogFragment() {

    private var _binding: FragmentPopupBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding is null and can't be accessed!"
        }

    companion object {
        fun newPopUp(title: String, description: String, imageResId: Int): PopupFragment {
            val fragment = PopupFragment()
            val args = Bundle()
            args.putString("title", title)
            args.putString("description", description)
            args.putInt("imageResId", imageResId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopupBinding.inflate(layoutInflater, container, false)
        val tipTitle = binding.tipTitle
        val tipDescription = binding.tipDescription
        val tipImage = binding.tipImage

        arguments?.let {
            tipTitle.text = it.getString("title", "")
            tipDescription.text = it.getString("description", "")
            tipImage.setImageResource(it.getInt("imageResId", 0))
        }

        return binding.root
    }


}