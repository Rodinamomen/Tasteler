package com.example.recipemobileapp.Database

import androidx.lifecycle.LiveData
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
   suspend fun gellAllUsers(): List<User>
    @Query("SELECT * FROM User WHERE email LIKE :email AND password LIKE :password")
    fun readAllData(email: String, password: String): User
    @Query("SELECT EXISTS(SELECT * FROM User WHERE email = :email AND password= :password)")
    suspend fun isUserExist(email: String, password: String ): Boolean
    @Query("SELECT EXISTS(SELECT * FROM User WHERE email = :email )")
    suspend fun isEmailExist(email: String): Boolean

    @Query("SELECT * FROM User WHERE email= :email")
    suspend fun searchByEmail(email: String): User



}