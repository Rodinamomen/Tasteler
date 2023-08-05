package com.example.recipemobileapp.Authentication.signup.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Repo.SignUpRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(val signUpRepo: SignUpRepo):ViewModel() {
    private val _userdata= MutableLiveData<Boolean>()
    val userdata: LiveData<Boolean> = _userdata
    fun insertUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            signUpRepo.insertUser(user)
        }
    }
    fun isUserExist(email: String, password: String){
        viewModelScope.launch {
            val response = signUpRepo.isUserExist(email,password)
            _userdata.value=response
        }
    }
}