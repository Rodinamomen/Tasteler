package com.example.recipemobileapp.HomeActivity.home.Repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Recipe
import com.example.recipemobileapp.Database.Userwithmeals
import com.example.recipemobileapp.Database.Wishlist

interface MealRepo {
    suspend fun getAllMealsFromAPI(randomChar: Char): Recipe
    suspend fun getRandomMealFromAPI(): Recipe




    suspend fun getSearchResultFromAPI(search : String): Recipe


    suspend fun insertIntofavs(wishlist: Wishlist)
    suspend fun getFavouriteMealsWithUserId(userId:Int): Userwithmeals?

}