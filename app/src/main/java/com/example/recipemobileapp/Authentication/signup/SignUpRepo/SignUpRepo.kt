package com.example.recipemobileapp.Repo

import com.example.recipemobileapp.Database.User

interface SignUpRepo {
    suspend fun insertUser(user: User)
    suspend fun deleteUser(user: User)
}