package com.example.haggle_x.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haggle_x.MainActivity
import com.example.haggle_x.R


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var mainActivity: MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }


}