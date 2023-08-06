package com.example.recipemobileapp.Database.localDataSource

import androidx.lifecycle.LiveData
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

}
