package com.example.recipemobileapp.HomeActivity.home.Repo

import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Recipe

interface MealRepo {
    suspend fun getAllMealsFromAPI(): Recipe
    suspend fun getRandomMealFromAPI(): Recipe
}