package com.example.haggle_x.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.haggle_x.MainActivity
import com.example.haggle_x.R
import com.example.haggle_x.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.signButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_verifyFragment)
        }
    }
}