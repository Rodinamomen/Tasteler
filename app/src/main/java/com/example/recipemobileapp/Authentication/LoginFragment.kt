package com.example.recipemobileapp.Authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.recipemobileapp.R


class LoginFragment : Fragment() {
  lateinit var  button_signup:Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button_signup= view.findViewById(R.id.button_signup)
        button_signup.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}