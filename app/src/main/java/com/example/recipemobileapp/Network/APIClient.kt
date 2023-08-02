package com.example.recipemobileapp.Network

import com.example.recipemobileapp.Database.Recipe

object APIClient:RemoteDataSource {
    override suspend fun getAllMeals(): Recipe {
        return APIHelper.retrofit.create(APIService::class.java).getAllMeals()
    }

}