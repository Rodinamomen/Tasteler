package com.example.recipemobileapp.Database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class,Meal::class,Wishlist::class], version = 6)
abstract class UserDataBase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun userWithMealsDao(): UserwithMealsDao
    abstract fun userDao():UserDao
    companion object{
        @Volatile
        private var INSTANCE:UserDataBase?= null
        fun getInstance(context: Context):UserDataBase{
            Log.d("TAG", "getInstance: I got this far")
            return INSTANCE?: synchronized(this){
                INSTANCE?: Room.databaseBuilder(
                    context.applicationContext,
                    UserDataBase::class.java,
                    "Userdata"
                )   .fallbackToDestructiveMigration()
                    .build()
                    .also {
                   INSTANCE= it
                }
            }
        }
    }
}