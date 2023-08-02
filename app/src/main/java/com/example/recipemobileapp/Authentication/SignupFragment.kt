package com.example.recipemobileapp.Authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.database
import com.example.recipemobileapp.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignupFragment : Fragment() {
    lateinit var email: TextInputEditText
    lateinit var password: TextInputEditText
    lateinit var firstname:TextInputEditText
    lateinit var lastname:TextInputEditText
    lateinit var signupbtn: Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val database= com.example.recipemobileapp.Database.database.getInstance(requireContext()).userDao()
        firstname=view.findViewById(R.id.textinput_first_name)
        lastname=view.findViewById(R.id.textinput_last_name)
        email=view.findViewById(R.id.textinput_email)
        password=view.findViewById(R.id.textinput_password)
        signupbtn=view.findViewById(R.id.button_signup_signupfragment)
        signupbtn.setOnClickListener{
            GlobalScope.launch {
                database.insertUser(User(userid = 0, firstname.text.toString(),lastname.text.toString(),email.text.toString(),password.text.toString()))
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}