package com.example.recipemobileapp.HomeActivity.home.Repo

import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Recipe
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.Database.localDataSource.LocalDataSource
import com.example.recipemobileapp.Network.RemoteDataSource

class SearchRepoImpl(val remoteDataSource: RemoteDataSource, val localDataSource: LocalDataSource):
    SearchRepo {
     override suspend fun getSearchResultFromAPI(search : String): Recipe {
        return remoteDataSource.getSearchResult(search)
    }
    override suspend fun insertMeal(meal: Meal) {
        localDataSource.insertMeal(meal)
    }
    override suspend fun getMealById(id: String):Meal {
        return localDataSource.getMealById(id)
    }

    override suspend fun getUserIdByEmail(email: String): User {
        return localDataSource.getUserIdByEmail(email)
    }
    override suspend fun insertIntofavs(wishlist: Wishlist) {
        return localDataSource.insertIntofavs(wishlist)
    }



}