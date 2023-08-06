package com.example.recipemobileapp.Authentication.Login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemobileapp.Authentication.Login.LoginRepo.LoginRepo
import com.example.recipemobileapp.Database.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Response

class LoginViewModel(val loginRepo: LoginRepo):ViewModel() {
    private val _usersdata = MutableLiveData<List<User>>()
    val usersdata: LiveData<List<User>> = _usersdata
    private val _userdata= MutableLiveData<Boolean>()
    val userdata: LiveData<Boolean> = _userdata

   private val _searchByeemail=MutableLiveData<User>()
    val searchByEmail= _searchByeemail

    private val _isEmailExists= MutableLiveData<Boolean>()
    val isEmailExists= _isEmailExists
     fun getAllUsers(){
         viewModelScope.launch{
             val response= loginRepo.getAllUsers()
             Log.d("rodina", "getAllUsers:$response")
             _usersdata.value = response
         }
    }
  /*  fun readAllData(email: String, password: String){
        viewModelScope.launch{
            val response= loginRepo.readAllData(email,password)
            _userdata.value=response
        }
    }*/
    fun isUserExist(email: String, password: String){
        viewModelScope.launch {
             val response = loginRepo.isUserExist(email,password)
            _userdata.value=response
        }
    }
    fun  searchByEmail(email:String){
        viewModelScope.launch {
            val response= loginRepo.searchByEmail(email)
            _searchByeemail.value = response
        }
    }
    fun isEmailExists(email: String){
        viewModelScope.launch{
            val response= loginRepo.isEmailExist(email)
            _isEmailExists.value= response
        }
    }



}
