package com.example.recipemobileapp.Authentication.Login.LoginRepo

import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.localDataSource.LocalDataSource

class LoginRepoImp(val localDataSource: LocalDataSource):LoginRepo {
    override suspend fun getAllUsers(): List<User> {
        return localDataSource.getAllUsers()
    }

}