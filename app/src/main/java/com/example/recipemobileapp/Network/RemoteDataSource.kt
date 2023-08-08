package com.example.recipemobileapp.Network

import androidx.lifecycle.MutableLiveData
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Recipe
import retrofit2.http.Query

interface RemoteDataSource {
    suspend fun getAllMeals(randomChar: Char): Recipe
    suspend fun getRandomMeal(): Recipe
    suspend fun getSearchResult( search :String): Recipe



}