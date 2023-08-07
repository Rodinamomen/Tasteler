package com.example.recipemobileapp.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User (
    @PrimaryKey(autoGenerate = true)
    val userid:Int = 0,
    val username:String,
    val lastName:String,
    val email:String,
    val password:String
)