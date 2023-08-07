package com.example.recipemobileapp.Network

import com.example.recipemobileapp.Database.Recipe

object APIClient:RemoteDataSource {
    override suspend fun getAllMeals(randomChar: Char): Recipe {
        return APIHelper.retrofit.create(APIService::class.java).getAllMeals(randomChar)
    }

    override suspend fun getRandomMeal(): Recipe {
        return APIHelper.retrofit.create(APIService::class.java).getRandomMeal()
    }

    override suspend fun getSearchResult(search: String): Recipe {
        return APIHelper.retrofit.create(APIService::class.java).getSearchResult(search)
    }
}