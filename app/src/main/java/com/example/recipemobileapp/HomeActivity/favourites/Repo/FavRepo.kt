package com.example.recipemobileapp.HomeActivity.favourites.Repo

import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Userwithmeals
import com.example.recipemobileapp.Database.Wishlist

interface FavRepo {
    suspend fun deleteMeal(meal: Meal)
    suspend fun getuserWithMeals(): List<Userwithmeals>
    suspend fun deleteWishlist(wishlist: Wishlist)
}