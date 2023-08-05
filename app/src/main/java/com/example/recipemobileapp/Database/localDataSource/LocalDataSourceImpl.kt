package com.example.recipemobileapp.Database.localDataSource

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.UserDao
import com.example.recipemobileapp.Database.UserDataBase

class LocalDataSourceImpl(context:Context):LocalDataSource {
    private lateinit var userDao: UserDao
    init {
        val db: UserDataBase = UserDataBase.getInstance(context)
        userDao = db.userDao()
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
}