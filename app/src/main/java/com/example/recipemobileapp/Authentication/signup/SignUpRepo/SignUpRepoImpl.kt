package com.example.recipemobileapp.Authentication.signup.SignUpRepo

import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.localDataSource.LocalDataSource
import com.example.recipemobileapp.Repo.SignUpRepo

class SignUpRepoImpl(val localDataSource: LocalDataSource): SignUpRepo {
    override suspend fun insertUser(user: User) {
        localDataSource.insertUser(user)
    }

    override suspend fun deleteUser(user: User) {
        localDataSource.deleteUser(user)
    }

}