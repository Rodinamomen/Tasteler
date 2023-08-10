package com.example.recipemobileapp.HomeActivity.home.Repo

import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Recipe
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.Database.localDataSource.LocalDataSource
import com.example.recipemobileapp.Network.RemoteDataSource

class MealRepoImpl(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource): MealRepo {

    override suspend fun getAllMealsFromAPI(randomChar: Char): Recipe {
        return remoteDataSource.getAllMeals(randomChar)
    }
    override suspend fun getRandomMealFromAPI(): Recipe {
        return remoteDataSource.getRandomMeal()

    }
    override suspend fun insertMeal(meal: Meal) {
        localDataSource.insertMeal(meal)
    }
    override suspend fun getUserIdByEmail(email: String): User {
        return localDataSource.getUserIdByEmail(email)
    }
    override suspend fun insertIntofavs(wishlist: Wishlist) {
        return localDataSource.insertIntofavs(wishlist)
    }
    override suspend fun isFavourite(userid: Int, idMeal: String): Boolean {
        return localDataSource.isFavourite(userid,idMeal)
    }
    override suspend fun deleteWishlist(wishlist: Wishlist) {
        localDataSource.deleteWishlist(wishlist)
    }
    override suspend fun deleteMeal(meal: Meal) {
        localDataSource.deleteMeal(meal)
    }
}