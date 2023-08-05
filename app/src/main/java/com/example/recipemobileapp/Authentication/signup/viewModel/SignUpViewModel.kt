package com.example.recipemobileapp.Authentication.signup.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Repo.SignUpRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(val signUpRepo: SignUpRepo):ViewModel() {
    fun insertUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            signUpRepo.insertUser(user)
        }
    }
}