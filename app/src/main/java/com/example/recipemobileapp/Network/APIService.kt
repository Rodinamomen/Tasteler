package com.example.recipemobileapp.Network

import com.example.recipemobileapp.Models.Recipe
import retrofit2.http.GET

interface APIService {
    @GET("/api/json/v1/1/search.php?s")
    suspend fun getAllMeals(): Recipe

}