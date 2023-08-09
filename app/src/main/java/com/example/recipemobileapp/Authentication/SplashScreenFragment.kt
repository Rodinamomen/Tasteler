package com.example.recipemobileapp.Authentication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.recipemobileapp.Authentication.Login.view.LoginFragment
import com.example.recipemobileapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment() {
    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL_KEY = "email_key"
        const val PASSWORD_KEY = "password_key"
    }
    lateinit var editor: SharedPreferences.Editor
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch {
            delay(500)
            sharedPreferences= requireActivity().getSharedPreferences(
                LoginFragment.SHARED_PREFS,
                Context.MODE_PRIVATE)
            editor=sharedPreferences.edit()
            if(sharedPreferences.getString(EMAIL_KEY,null)==null && sharedPreferences.getString(PASSWORD_KEY,null)==null){
                findNavController().navigate(R.id.action_splashScreenFragment2_to_loginFragment)
            }else{
                findNavController().navigate(R.id.action_splashScreenFragment2_to_homeActivity)
                requireActivity().finish()
            }
        }
        // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_splash_screen2, container, false)

        }
}