package com.example.recipemobileapp.Database.localDataSource

import android.content.Context
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
}