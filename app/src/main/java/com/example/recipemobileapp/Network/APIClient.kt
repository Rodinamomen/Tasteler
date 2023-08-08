package com.example.recipemobileapp.Network

import androidx.lifecycle.MutableLiveData
import com.example.recipemobileapp.Database.Meal
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