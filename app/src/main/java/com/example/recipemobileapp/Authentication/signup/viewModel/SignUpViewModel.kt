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

    private val _isEmailExists= MutableLiveData<Boolean>()
    val isEmailExists= _isEmailExists
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
    fun isEmailExists(email: String){
        viewModelScope.launch{
            val response= signUpRepo.isEmailExist(email)
            _isEmailExists.value = response
        }
    }
}