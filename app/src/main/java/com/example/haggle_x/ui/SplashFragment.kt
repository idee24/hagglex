package com.example.haggle_x.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.haggle_x.MainActivity
import com.example.haggle_x.R
import kotlinx.coroutines.delay

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var mainActivity: MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity

        lifecycleScope.launchWhenResumed {
            delay(3000)
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }


}