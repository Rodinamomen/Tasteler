package com.example.recipemobileapp.Authentication.Login.LoginRepo

import androidx.lifecycle.LiveData
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.UserDao
import com.example.recipemobileapp.Database.localDataSource.LocalDataSource

class LoginRepoImp(val localDataSource: LocalDataSource):LoginRepo {
    override suspend fun getAllUsers(): List<User>{
        return localDataSource.getAllUsers()
    }

    override suspend fun readAllData(email: String, password: String): User {
        return localDataSource.readAllData(email,password)
    }

    override suspend fun isUserExist(email: String, password: String): Boolean {
        return localDataSource.isUserExist(email,password)
    }
    override suspend fun isEmailExist(email: String):Boolean{
      return  localDataSource.isEmailExist(email)
    }
    override suspend fun searchByEmail(email: String): User{
        return localDataSource.searchByEmail(email)
    }


}