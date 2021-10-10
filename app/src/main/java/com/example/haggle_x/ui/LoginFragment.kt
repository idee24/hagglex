package com.example.haggle_x.ui

import LoginMutation
import ResendVerificationQuery
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
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
import com.pixplicity.easyprefs.library.Prefs


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
        val username = isNotEmpty(binding.emailField, binding.emailLayout)
        val password =
            isNotEmpty(binding.passwordField, binding.passwordLayout)
        return username && password
    }

    private fun initLogin() {

        lifecycleScope.launchWhenStarted {

            showLoader(true, binding.mainLoader.appLoader)

            val email = binding.emailField.text.toString().trim()
            val password = binding.passwordField.text.toString().trim()

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
                Prefs.putString("auth_key", response.data?.login?.token ?: "")
                if (response.data?.login?.user?.emailVerified == true) {
                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                }
                else {
                    showLoader(true, binding.mainLoader.appLoader)
                    apolloClient(requireContext()).query(ResendVerificationQuery(email)).await()
                    showLoader(false, binding.mainLoader.appLoader)
                    val bundle = bundleOf("email_key" to binding.emailField.text.toString().trim())
                    findNavController().navigate(R.id.action_loginFragment_to_verifyFragment, bundle)
                }
                return@launchWhenStarted
            }


        }
    }

}