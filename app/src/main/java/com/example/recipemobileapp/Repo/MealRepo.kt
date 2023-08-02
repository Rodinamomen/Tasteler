package com.example.recipemobileapp.Repo

import com.example.recipemobileapp.Database.Recipe

interface MealRepo {
    suspend fun getMealFromAPI(): Recipe
}