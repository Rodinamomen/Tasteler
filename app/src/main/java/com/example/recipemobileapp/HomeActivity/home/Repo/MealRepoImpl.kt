package com.example.recipemobileapp.HomeActivity.home.Repo

import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Recipe
import com.example.recipemobileapp.Network.RemoteDataSource

class MealRepoImpl(val remoteDataSource: RemoteDataSource):MealRepo {
    override suspend fun getAllMealsFromAPI(randomChar: Char): Recipe {
        return remoteDataSource.getAllMeals(randomChar)
    }

    override suspend fun getRandomMealFromAPI(): Recipe {
        return remoteDataSource.getRandomMeal()

    }

    override suspend fun getSearchResultFromAPI(search : String): Recipe {
        return remoteDataSource.getSearchResult(search)
    }


}