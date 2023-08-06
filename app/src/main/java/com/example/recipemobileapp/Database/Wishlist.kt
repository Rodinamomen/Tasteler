package com.example.recipemobileapp.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["userid","mealid"])
data class Wishlist (
     val userid : Int,
     val mealid :Int
)