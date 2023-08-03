package com.example.recipemobileapp.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)
    @Delete
    suspend fun deleteUser(user: User)
    @Query("SELECT * FROM User")
    suspend fun gellAllUsers():List<User>
}