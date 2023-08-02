package com.example.recipemobileapp.Network

import com.example.recipemobileapp.Database.Recipe

interface RemoteDataSource {
    suspend fun getAllMeals(): Recipe
}