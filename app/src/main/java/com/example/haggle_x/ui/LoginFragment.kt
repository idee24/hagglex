package com.example.haggle_x.ui

import LoginMutation
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.haggle_x.Constants
import com.example.haggle_x.MainActivity
import com.example.haggle_x.R
import com.example.haggle_x.apolloClient
import com.example.haggle_x.databinding.FragmentLoginBinding


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding.createAccountTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.loginButton.setOnClickListener {
            if (isValidLogin()) {
                initLogin()
            }
        }
    }

    private fun isValidLogin(): Boolean {
        val username = isNotEmpty(mainActivity, binding.emailField, binding.emailLayout)
        val password = isNotEmpty(mainActivity, binding.passwordField, binding.passwordLayout)
        return username && password
    }

    private fun initLogin() {

        lifecycleScope.launchWhenStarted {

            showLoader(true, binding.mainLoader.appLoader)

            val email = binding.emailField.toString().trim()
            val password = binding.passwordField.toString().trim()

            val response = try {
                apolloClient(requireContext()).mutate(LoginMutation(email, password)).await()
            }
            catch (e: ApolloException) {
                showLoader(false, binding.mainLoader.appLoader)
                CustomDialog(mainActivity, e.message ?: "A problem Occurred") {}.show()
                return@launchWhenStarted
            }
            showLoader(false, binding.mainLoader.appLoader)
            if (response.hasErrors() || response.data?.login == null) {
                CustomDialog(mainActivity, response.errors?.get(0)?.message ?: "A problem Occurred") {}.show()
                return@launchWhenStarted
            }
            else {
                findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                return@launchWhenStarted
            }


        }
    }

}