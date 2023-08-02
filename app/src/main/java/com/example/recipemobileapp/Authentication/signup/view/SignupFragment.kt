package com.example.recipemobileapp.Authentication.signup.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.recipemobileapp.Authentication.signup.SignUpRepo.SignUpRepoImpl
import com.example.recipemobileapp.Authentication.signup.viewModel.SignUpViewModel
import com.example.recipemobileapp.Authentication.signup.viewModel.SignUpViewModelFactory
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.localDataSource.LocalDataSourceImpl
import com.example.recipemobileapp.R
import com.google.android.material.textfield.TextInputLayout

class SignupFragment : Fragment() {
    lateinit var email: TextInputLayout
    lateinit var password: TextInputLayout
    lateinit var firstname:TextInputLayout
    lateinit var lastname:TextInputLayout
    lateinit var signupbtn: Button
    lateinit var signUpViewModel: SignUpViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gettingViewModelReady(requireContext())

        firstname=view.findViewById(R.id.textinput_first_name)
        lastname=view.findViewById(R.id.textinput_last_name)
        email=view.findViewById(R.id.textinput_email)
        password=view.findViewById(R.id.textinput_password)
        signupbtn=view.findViewById(R.id.button_signup)

        signupbtn.setOnClickListener{
            signUpViewModel.insertUser(
                User(0, firstname.editText?.text.toString(),lastname.editText?.
                text.toString(),email.editText?.text.toString(),password.editText?.text.toString())
            )
        }

    }
    private fun gettingViewModelReady(context: Context) {
        val signUpViewModelFactory = SignUpViewModelFactory(
            SignUpRepoImpl(LocalDataSourceImpl(context))
        )
        signUpViewModel = ViewModelProvider(this,signUpViewModelFactory).get(SignUpViewModel::class.java)
    }
}