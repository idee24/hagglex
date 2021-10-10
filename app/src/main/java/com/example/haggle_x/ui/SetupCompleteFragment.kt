package com.example.haggle_x.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.haggle_x.MainActivity
import com.example.haggle_x.R
import com.example.haggle_x.databinding.FragmentSetupCompleteBinding


class SetupCompleteFragment : Fragment(R.layout.fragment_setup_complete) {

    private lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentSetupCompleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSetupCompleteBinding.bind(view)

        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_setupCompleteFragment_to_dashboardFragment)
        }
    }

}