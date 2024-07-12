package com.example.recipemobileapp.Authentication.Login.LoginRepo

import androidx.lifecycle.LiveData
import com.example.recipemobileapp.Database.User

interface LoginRepo {
  suspend  fun getAllUsers():List<User>
 suspend fun isUserExist(email: String, password: String ) : Boolean
    suspend fun isEmailExist(email: String):Boolean
    suspend fun searchByEmail(email: String): User
}