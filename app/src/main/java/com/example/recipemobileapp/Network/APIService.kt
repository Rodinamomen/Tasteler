package com.example.recipemobileapp.Network

import androidx.lifecycle.MutableLiveData
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Recipe
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("/api/json/v1/1/search.php?")
    suspend fun getAllMeals(@Query("s") randomChar: Char): Recipe

    @GET("/api/json/v1/1/random.php")
    suspend fun getRandomMeal(): Recipe


    @GET("/api/json/v1/1/search.php")
    suspend fun getSearchResult(@Query("s") search :String): Recipe


    @GET("/api/json/v1/1/lookup.php?")
    suspend fun getMealByID(@Query("i") ID: Int): Meal




}