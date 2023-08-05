package com.example.recipemobileapp.HomeActivity.home.Repo

import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Recipe
import com.example.recipemobileapp.Database.Userwithmeals

interface MealRepo {
    suspend fun getAllMealsFromAPI(randomChar: Char): Recipe
    suspend fun getRandomMealFromAPI(): Recipe
    suspend fun getFavouriteMealsWithUserId(userId:Int): Userwithmeals?
}