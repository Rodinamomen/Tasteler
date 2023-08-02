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
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignupFragment : Fragment() {
    lateinit var email: TextInputLayout
    lateinit var password: TextInputLayout
    lateinit var firstname:TextInputLayout
    lateinit var lastname:TextInputLayout
    lateinit var signupbtn: Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val database= database.getInstance(requireContext()).userDao()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val database= database.getInstance(requireContext()).userDao()
        firstname=view.findViewById(R.id.textinput_first_name)
        lastname=view.findViewById(R.id.textinput_last_name)
        email=view.findViewById(R.id.textinput_email)
        password=view.findViewById(R.id.textinput_password)
        signupbtn=view.findViewById(R.id.button_signup)
        signupbtn.setOnClickListener{
          GlobalScope.launch {
              database.insertUser(User(0, firstname.editText?.text.toString(),lastname.editText?.text.toString(),email.editText?.text.toString(),password.editText?.text.toString()))
           }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}