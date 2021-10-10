package com.example.haggle_x.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.haggle_x.R
import com.example.haggle_x.databinding.DialogBinding
import com.google.android.material.textfield.TextInputLayout

/**
 *@Created by Yerimah on 10/10/2021.
 */

fun isNotEmpty(inputField: EditText, inputLayout: TextInputLayout): Boolean {
    val  target = inputField.text.toString().trim()
    return if (!TextUtils.isEmpty(target)) {
        inputLayout.isErrorEnabled = false
        true
    } else {
        inputLayout.error = "Field Cannot be empty"
        false
    }
}

fun Fragment.showLoader(showLoader: Boolean, appLoader: RelativeLayout) {

    if (showLoader) {
        appLoader.visibility = View.VISIBLE
        appLoader.hideKeyboard()
        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
    else {
        appLoader.visibility = View.GONE
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}

fun View.hideKeyboard() {
    val hideAction = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    hideAction.hideSoftInputFromWindow(windowToken, 0)
}


class CustomDialog(activity: FragmentActivity,
                   private val message: String,
                   private val callBack: () -> Unit): Dialog(activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = DialogBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        binding.messageTextView.text = message

        binding.dismissTextView.setOnClickListener {
            callBack()
            dismiss()
        }
    }
}