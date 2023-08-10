package com.example.recipemobileapp.Database.localDataSource

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.MealDao
import com.example.recipemobileapp.Database.Recipe
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.UserDao
import com.example.recipemobileapp.Database.UserDataBase
import com.example.recipemobileapp.Database.UserwithMealsDao
import com.example.recipemobileapp.Database.Userwithmeals
import com.example.recipemobileapp.Database.Wishlist

class LocalDataSourceImpl(context:Context):LocalDataSource {
    private lateinit var userDao: UserDao
    private lateinit var userWithMealsDao: UserwithMealsDao
    private lateinit var mealDao: MealDao

    init {
        val db: UserDataBase = UserDataBase.getInstance(context)
        userDao = db.userDao()
        userWithMealsDao = db.userWithMealsDao()
        mealDao= db.mealDao()
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

    override suspend fun insertMeal(meal: Meal) {
        mealDao.insertMeal(meal)
    }

    override suspend fun deleteMeal(meal: Meal) {
        mealDao.deleteMeal(meal)
    }

    override suspend fun getuserWithMeals(): List<Userwithmeals> {
        return userWithMealsDao.getuserWithMeals()
    }

    override suspend fun getMealById(id:String): Meal {
        return mealDao.getMealById(id)
    }

    override suspend fun getUserIdByEmail(email: String): User {
        return userDao.searchByEmail(email)
    }

    override suspend fun deleteWishlist(wishlist: Wishlist) {
        userWithMealsDao.deleteWishlist(wishlist)
    }

    override suspend fun isFavourite(userid: Int, idMeal: String): Boolean {
        return userWithMealsDao.isFavourite(userid,idMeal)
    }

    override suspend fun getFavouriteMealsWithUserId(userId: Int): Userwithmeals? {
        return userWithMealsDao.getUserWithMealsById(userId)
    }

    override suspend fun insertIntofavs(wishlist: Wishlist) {
        return userWithMealsDao.insert(wishlist)
    }


}