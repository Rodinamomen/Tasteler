package com.example.recipemobileapp.Database.localDataSource

import com.example.recipemobileapp.Database.User

interface LocalDataSource {
    suspend fun insertUser(user: User)
    suspend fun deleteUser(user: User)
}