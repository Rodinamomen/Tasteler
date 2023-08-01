package com.example.recipemobileapp.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MealDao {
    @Query("SELECT * From USER")
    suspend fun getAllUsers():List<User>
    @Insert
    suspend fun insertUser(user: User)

    //TODO(Search-Delete)

}