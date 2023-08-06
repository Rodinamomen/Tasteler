package com.example.recipemobileapp.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface UserwithMealsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userwithmeals: Wishlist)
    @Transaction
    @Query("DELETE FROM WishList WHERE WishList.mealid = :mealid")
    suspend fun removeAllMealsByMealId(mealid: String)
    @Transaction
    @Query("DELETE  FROM WishList WHERE WishList.userid = :userid")
    suspend fun removeAllUsersByUserid(userid: Int)
    @Delete
    suspend fun delete(userwithmeals: Wishlist)
    @Transaction
    @Query("SELECT * FROM User")
   fun getuserWithMeals(): LiveData<List<Userwithmeals>>

    @Transaction
    @Query("SELECT * FROM User WHERE userid = :userid")
    fun getUserWithMealsById(userid: Int): Userwithmeals?
}