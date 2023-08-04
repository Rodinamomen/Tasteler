package com.example.recipemobileapp.HomeActivity.home.Repo

import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Recipe
import com.example.recipemobileapp.Network.RemoteDataSource

class MealRepoImpl(val remoteDataSource: RemoteDataSource):MealRepo {
    override suspend fun getAllMealsFromAPI(): Recipe {
        return remoteDataSource.getAllMeals()
    }

    override suspend fun getRandomMealFromAPI(): Recipe {
        return remoteDataSource.getRandomMeal()
    }
}