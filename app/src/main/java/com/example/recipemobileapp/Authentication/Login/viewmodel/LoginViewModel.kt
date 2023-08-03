package com.example.recipemobileapp.Authentication.Login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemobileapp.Authentication.Login.LoginRepo.LoginRepo
import com.example.recipemobileapp.Database.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginViewModel(val loginRepo: LoginRepo):ViewModel() {
   suspend fun getAllUsers():List<User>{
          return loginRepo.getAllUsers()
    }
}