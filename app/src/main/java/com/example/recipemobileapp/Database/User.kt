package com.example.recipemobileapp.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipemobileapp.Models.Meal

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val username:String,
    val lastName:String,
    val email:String,
    val password:String,
    val userImg:Int,
//    val favouriteMeals: List<Meal>
)
