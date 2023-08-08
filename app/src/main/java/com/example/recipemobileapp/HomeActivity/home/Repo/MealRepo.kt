package com.example.recipemobileapp.HomeActivity.home.Repo

import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Recipe
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.Wishlist

interface MealRepo {
    suspend fun getAllMealsFromAPI(randomChar: Char): Recipe
    suspend fun getRandomMealFromAPI(): Recipe
    suspend fun insertIntofavs(wishlist: Wishlist)
    suspend fun insertMeal(meal: Meal)
    suspend fun getUserIdByEmail(email:String): User
}