package com.example.recipemobileapp.Network

import androidx.appcompat.widget.ThemedSpinnerAdapter.Helper
import com.example.recipemobileapp.Models.Recipe

object APIClient:RemoteDataSource {
    override suspend fun getAllMeals(): Recipe{
        return APIHelper.retrofit.create(APIService::class.java).getAllMeals()
    }

}