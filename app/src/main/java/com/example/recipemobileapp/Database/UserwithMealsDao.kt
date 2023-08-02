package com.example.recipemobileapp.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserwithMealsDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insert(userwithmeals: Userwithmeals)
  @Query("DELETE  FROM WishList WHERE WishList.mealid = :mealid")
  suspend fun removeAllMealsByMealid(mealid: String)
  @Query("DELETE  FROM WishList WHERE WishList.userid = :userid")
  suspend fun removeAllUsersByUserid(userid: Int)
  @Delete()
  suspend fun delte(userwithmeals: Userwithmeals)
  @Query("SELECT * FROM User")
   fun getuserWithMeals(): LiveData<List<Userwithmeals>>
}