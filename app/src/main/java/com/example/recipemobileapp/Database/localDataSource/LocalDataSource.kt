package com.example.recipemobileapp.Database.localDataSource

import androidx.lifecycle.LiveData
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Recipe
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.Userwithmeals
import com.example.recipemobileapp.Database.Wishlist

interface LocalDataSource {
    suspend fun insertUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun getAllUsers():List<User>
    suspend fun readAllData(email:String, password:String): User
    suspend fun isUserExist(email: String, password: String ) : Boolean

    suspend fun getFavouriteMealsWithUserId(userId:Int): Userwithmeals?
    suspend fun insertIntofavs(wishlist: Wishlist)
    suspend fun isEmailExist(email: String):Boolean
    suspend fun searchByEmail(email: String): User

    suspend fun insertMeal(meal: Meal)
    suspend fun deleteMeal(meal: Meal)
    suspend fun getuserWithMeals(): List<Userwithmeals>
    suspend fun getMealById(id:String): Meal

    suspend fun getUserIdByEmail(email:String):User
    suspend fun deleteWishlist(wishlist: Wishlist)
    suspend fun isFavourite(userid:Int,idMeal:String): Boolean


}