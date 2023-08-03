package com.example.recipemobileapp.Authentication.signup.viewModel


import com.example.recipemobileapp.Repo.SignUpRepo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
class SignUpViewModelFactory(val signUpRepo:SignUpRepo): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            SignUpViewModel(signUpRepo) as T
        }else{
            throw IllegalArgumentException("SignUpViewModel class not found")
        }
    }
}
