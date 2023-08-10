package com.example.recipemobileapp.HomeActivity.home.Repo

import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Recipe
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.Wishlist

interface SearchRepo {
    suspend fun getSearchResultFromAPI(search : String): Recipe
    suspend fun insertMeal(meal: Meal)
    suspend fun getMealById(id:String): Meal
    suspend fun getUserIdByEmail(email:String): User
    suspend fun insertIntofavs(wishlist: Wishlist)
    suspend fun isFavourite(userid:Int,idMeal:String): Boolean

}