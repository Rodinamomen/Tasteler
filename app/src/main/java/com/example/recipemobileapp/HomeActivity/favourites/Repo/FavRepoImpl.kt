package com.example.recipemobileapp.HomeActivity.favourites.Repo

import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Recipe
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.Userwithmeals
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.Database.localDataSource.LocalDataSource
import com.example.recipemobileapp.Network.RemoteDataSource

class FavRepoImpl(val remoteDataSource: RemoteDataSource,
                  val localDataSource: LocalDataSource): FavRepo {
    override suspend fun deleteMeal(meal: Meal) {
        localDataSource.deleteMeal(meal)
    }

    override suspend fun getuserWithMeals(): List<Userwithmeals> {
        return localDataSource.getuserWithMeals()
    }

    override suspend fun deleteWishlist(wishlist: Wishlist) {
        localDataSource.deleteWishlist(wishlist)
    }
}