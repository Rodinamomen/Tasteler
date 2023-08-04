package com.example.recipemobileapp.Network

import com.example.recipemobileapp.Database.Recipe
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("/api/json/v1/1/search.php?")
    suspend fun getAllMeals(@Query("s") randomChar: Char): Recipe

    @GET("/api/json/v1/1/random.php")
    suspend fun getRandomMeal(): Recipe
}