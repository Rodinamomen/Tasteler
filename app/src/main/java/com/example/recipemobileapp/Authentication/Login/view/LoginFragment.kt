package com.example.recipemobileapp.Authentication.Login.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.recipemobileapp.Authentication.Login.LoginRepo.LoginRepo
import com.example.recipemobileapp.Authentication.Login.LoginRepo.LoginRepoImp
import com.example.recipemobileapp.Authentication.Login.viewmodel.LoginViewModel
import com.example.recipemobileapp.Authentication.Login.viewmodel.LoginViewModelFactory
import com.example.recipemobileapp.Authentication.signup.SignUpRepo.SignUpRepoImpl
import com.example.recipemobileapp.Authentication.signup.viewModel.SignUpViewModel
import com.example.recipemobileapp.Authentication.signup.viewModel.SignUpViewModelFactory
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.localDataSource.LocalDataSourceImpl
import com.example.recipemobileapp.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
  lateinit var  button_signup:Button
  lateinit var loginViewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        gettingViewModelReady(requireContext())
        super.onViewCreated(view, savedInstanceState)
        button_signup= view.findViewById(R.id.button_signup)
        button_signup.setOnClickListener{
            view.findNavController().navigate(R.id.signupFragment)
        }

    }
    private fun gettingViewModelReady(context: Context) {
        val loginViewModelFactory = LoginViewModelFactory(
            LoginRepoImp(LocalDataSourceImpl(context))
        )
        loginViewModel=ViewModelProvider(this,loginViewModelFactory).get(LoginViewModel::class.java)
    }
}