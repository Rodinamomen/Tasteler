package com.example.recipemobileapp.Repo

import com.example.recipemobileapp.Models.Recipe

interface MealRepo {
    suspend fun getMealFromAPI():Recipe
}