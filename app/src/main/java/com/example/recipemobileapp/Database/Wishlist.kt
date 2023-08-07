package com.example.recipemobileapp.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["userid","idMeal"])
data class Wishlist (
     val userid : Int,
     val idMeal :String
)