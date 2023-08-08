package com.example.recipemobileapp.HomeActivity.favourites.Repo

import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Recipe
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.Userwithmeals
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.Database.localDataSource.LocalDataSource
import com.example.recipemobileapp.Network.RemoteDataSource

class FavRepoImpl(val remoteDataSource: RemoteDataSource, val localDataSource: LocalDataSource):
    FavRepo {

    override suspend fun getFavouriteMealsWithUserId(userId:Int): Userwithmeals?{
        return localDataSource.getFavouriteMealsWithUserId(userId)
    }

    override suspend fun insertMeal(meal: Meal) {
        localDataSource.insertMeal(meal)
    }

    override suspend fun deleteMeal(meal: Meal) {
        localDataSource.deleteMeal(meal)
    }

    override suspend fun getuserWithMeals(): List<Userwithmeals> {
        return localDataSource.getuserWithMeals()
    }

    override suspend fun getMealById(id: String):Meal {
        return localDataSource.getMealById(id)
    }

    override suspend fun getUserIdByEmail(email: String): User {
        return localDataSource.getUserIdByEmail(email)
    }

    override suspend fun deleteWishlist(wishlist: Wishlist) {
        localDataSource.deleteWishlist(wishlist)
    }

    override suspend fun insertIntofavs(wishlist: Wishlist) {
        return localDataSource.insertIntofavs(wishlist)
    }

    override suspend fun getMealByID(ID:Int):Meal{
        return remoteDataSource.getMealByID(ID)
    }

}