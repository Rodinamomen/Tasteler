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
    @Query("DELETE FROM WishList WHERE WishList.idMeal = :mealid")
    suspend fun removeAllMealsByMealId(mealid: Int)
    @Transaction
    @Query("DELETE  FROM WishList WHERE WishList.userid = :userid")
    suspend fun removeAllUsersByUserid(userid: Int)
    @Delete
    suspend fun deleteWishlist(userwithmeals: Wishlist)
    @Transaction
    @Query("SELECT * FROM USER")
    suspend fun getuserWithMeals(): List<Userwithmeals>

    @Transaction
    @Query("SELECT * FROM User WHERE userid = :userid")
    fun getUserWithMealsById(userid: Int): Userwithmeals?
}