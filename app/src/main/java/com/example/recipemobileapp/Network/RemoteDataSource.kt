package com.example.recipemobileapp.Network

import com.example.recipemobileapp.Models.Recipe

interface RemoteDataSource {
    suspend fun getAllMeals(): Recipe
}