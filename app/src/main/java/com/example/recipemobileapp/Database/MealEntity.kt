package com.example.recipemobileapp.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="Meal")
data class Meal(
    @PrimaryKey(autoGenerate = false) val idMeal: String,
    val strArea: String,
    val strCategory: String,
    val strMeal: String,
    val strMealThumb: String,
    val strTags: String?,
    val strInstructions:String,
    val strYoutube: String
)


