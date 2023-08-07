package com.example.recipemobileapp.Database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class Userwithmeals(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "userid",
        entityColumn = "idMeal",
        associateBy = Junction(Wishlist::class)
    )
    val meals : List<Meal>
)