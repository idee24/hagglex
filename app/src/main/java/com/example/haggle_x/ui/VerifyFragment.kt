package com.example.haggle_x.ui

import ResendVerificationQuery
import VerifyUserMutation
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.haggle_x.MainActivity
import com.example.haggle_x.R
import com.example.haggle_x.apolloClient
import com.example.haggle_x.databinding.FragmentVerifyBinding


class VerifyFragment : Fragment(R.layout.fragment_verify) {

    private lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentVerifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVerifyBinding.bind(view)

        binding.verifyButton.setOnClickListener {
            if (binding.codeField.text.toString().trim().isNotEmpty()) {
                verifyUserCode()
            }
            else {
                CustomDialog(mainActivity, "Please enter a code to continue") {}.show()
            }
        }
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.resendTextView.setOnClickListener {
            initResendCode()
        }

    }

    private fun verifyUserCode() {

        lifecycleScope.launchWhenStarted {

            showLoader(true, binding.mainLoader.appLoader)

            val response = try {
                apolloClient(requireContext())
                    .mutate(VerifyUserMutation(binding.codeField.toString().trim().toInt())).await()
            }
            catch (e: ApolloException) {
                showLoader(false, binding.mainLoader.appLoader)
                CustomDialog(mainActivity, e.message ?: "A problem Occurred") {}.show()
                return@launchWhenStarted
            }
            showLoader(false, binding.mainLoader.appLoader)
            if (response.hasErrors() || response.data?.verifyUser == null) {
                CustomDialog(mainActivity, response.errors?.get(0)?.message ?: "A problem Occurred") {}.show()
                return@launchWhenStarted
            }
            else {
                findNavController().navigate(R.id.action_verifyFragment_to_setupCompleteFragment)
                return@launchWhenStarted
            }


        }
    }

    private fun initResendCode() {

        lifecycleScope.launchWhenStarted {
            showLoader(true, binding.mainLoader.appLoader)

            val email = arguments?.getString("email_key") ?: ""

            val response = try {
                apolloClient(requireContext()).query(ResendVerificationQuery(email)).await()
            }
            catch (e: ApolloException) {
                showLoader(false, binding.mainLoader.appLoader)
                CustomDialog(mainActivity, e.message ?: "A problem Occurred") {}.show()
                return@launchWhenStarted
            }
            showLoader(false, binding.mainLoader.appLoader)
            if (response.hasErrors() || response.data?.resendVerificationCode == false) {
                CustomDialog(mainActivity, response.errors?.get(0)?.message ?: "Failed to Resend Code") {}.show()
                return@launchWhenStarted
            }
            else {
                CustomDialog(mainActivity, "Code Sent Successfully") {}.show()
                return@launchWhenStarted
            }
        }
    }
}