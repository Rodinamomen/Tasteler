package com.example.recipemobileapp.Authentication.Login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipemobileapp.Authentication.Login.LoginRepo.LoginRepo
import com.example.recipemobileapp.Authentication.signup.viewModel.SignUpViewModel
import com.example.recipemobileapp.Repo.SignUpRepo
class LoginViewModelFactory(val loginRepo: LoginRepo): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                LoginViewModel(loginRepo) as T
            }else{
                throw IllegalArgumentException("LoginViewModel class not found")
            }
        }
}