package com.example.recipemobileapp.Authentication.Login.LoginRepo

import com.example.recipemobileapp.Database.User

interface LoginRepo {
    suspend fun getAllUsers():List<User>
}