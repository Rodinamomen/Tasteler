package com.example.recipemobileapp.Database.localDataSource

import android.content.Context
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.UserDao
import com.example.recipemobileapp.Database.UserDataBase
import com.example.recipemobileapp.Database.UserwithMealsDao
import com.example.recipemobileapp.Database.Userwithmeals
import com.example.recipemobileapp.Database.Wishlist

class LocalDataSourceImpl(context:Context):LocalDataSource {
    private lateinit var userDao: UserDao
    private lateinit var userWithMealsDao: UserwithMealsDao
    init {
        val db: UserDataBase = UserDataBase.getInstance(context)
        userDao = db.userDao()
        userWithMealsDao = db.userWithMealsDao()
    }
    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }
    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    override suspend fun getAllUsers():List<User> {
      return  userDao.gellAllUsers()
    }

    override suspend fun readAllData(email: String, password: String): User {
        return userDao.readAllData(email,password)
    }
    override suspend fun isUserExist(email: String, password: String ) : Boolean{
        return userDao.isUserExist(email, password)
    }

    override suspend fun isEmailExist(email: String): Boolean {
        return userDao.isEmailExist(email)
    }

    override suspend fun searchByEmail(email: String): User {
      return  userDao.searchByEmail(email)
    }

    override suspend fun getFavouriteMealsWithUserId(userId: Int): Userwithmeals? {
        return userWithMealsDao.getUserWithMealsById(userId)
    }

    override suspend fun insertIntofavs(wishlist: Wishlist) {
        return userWithMealsDao.insert(wishlist)
    }
}