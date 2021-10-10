package com.example.haggle_x.ui

import RegisterMutation
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.haggle_x.MainActivity
import com.example.haggle_x.R
import com.example.haggle_x.apolloClient
import com.example.haggle_x.databinding.FragmentSignUpBinding
import java.util.*


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
            if (isValidSignUp()) {
                initSignUp()
            }
            else {
                CustomDialog(requireActivity(), "Please Ensure required Fields are filled and try again"){}.show()
            }
        }
    }

    private fun initSignUp() {


        lifecycleScope.launchWhenStarted {

            showLoader(true, binding.mainLoader.appLoader)

            val email = binding.emailField.toString().trim()
            val password = binding.passwordField.toString().trim()
            val userName = binding.userNameField.toString().trim()
            val phone = binding.phoneField.toString().trim()
            val refCode = binding.referralField.toString().trim()

            val response = try {
                apolloClient(requireContext()).mutate(RegisterMutation(email,
                    userName, password, phone,
                    Input.optional(refCode),
                    binding.ccPicker.selectedCountryCodeWithPlus,
                    binding.ccPicker.defaultCountryName,
                    binding.ccPicker.selectedCountryName,
                    Currency.getInstance(Locale("",
                        binding.ccPicker.selectedCountryNameCode)).currencyCode
                )).await()
            }
            catch (e: ApolloException) {
                showLoader(false, binding.mainLoader.appLoader)
                CustomDialog(mainActivity, e.message ?: "A problem Occurred") {}.show()
                return@launchWhenStarted
            }
            showLoader(false, binding.mainLoader.appLoader)
            if (response.hasErrors() || response.data?.register == null) {
                CustomDialog(mainActivity, response.errors?.get(0)?.message ?: "A problem Occurred") {}.show()
                return@launchWhenStarted
            }
            else {
                val bundle = bundleOf("email_key" to binding.emailField.text.toString().trim())
                findNavController().navigate(R.id.action_signUpFragment_to_verifyFragment, bundle)
                return@launchWhenStarted
            }


        }
    }

    private fun isValidSignUp(): Boolean {
        val email = isNotEmptyField(binding.emailField)
        val password = isNotEmptyField(binding.emailField)
        val username = isNotEmptyField(binding.emailField)
        val phone = isNotEmptyField(binding.emailField)

        return email && password && username && phone
    }


    private fun isNotEmptyField(inputField: EditText): Boolean {
        val  target = inputField.text.toString().trim()
        return !TextUtils.isEmpty(target)
    }


}