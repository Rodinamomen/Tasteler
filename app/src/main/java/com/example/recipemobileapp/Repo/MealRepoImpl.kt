package com.example.recipemobileapp.Repo

import com.example.recipemobileapp.Models.Recipe
import com.example.recipemobileapp.Network.RemoteDataSource

class MealRepoImpl(val remoteDataSource: RemoteDataSource):MealRepo {
    override suspend fun getMealFromAPI(): Recipe {
        return remoteDataSource.getAllMeals()
    }

}