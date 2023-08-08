package com.example.recipemobileapp.HomeActivity.favourites.Repo

import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Recipe
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.Userwithmeals
import com.example.recipemobileapp.Database.Wishlist

interface FavRepo {
    suspend fun getMealByID(ID:Int): Meal
    suspend fun insertIntofavs(wishlist: Wishlist)
    suspend fun getFavouriteMealsWithUserId(userId:Int): Userwithmeals?
    suspend fun insertMeal(meal: Meal)
    suspend fun deleteMeal(meal: Meal)
    suspend fun getuserWithMeals(): List<Userwithmeals>
    suspend fun getMealById(id:String): Meal
    suspend fun getUserIdByEmail(email:String): User
    suspend fun deleteWishlist(wishlist: Wishlist)
}