package com.example.recipemobileapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class,Meal::class,Userwithmeals::class], version = 1)
abstract class database : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun userWithMealsDao(): UserwithMealsDao
    abstract fun userDao():UserDao
    companion object{
        @Volatile
        private var INSTANCE:database?= null
        fun getInstance(context: Context):database{
            return INSTANCE?: synchronized(this){
                INSTANCE?: Room.databaseBuilder(
                    context.applicationContext,database::class.java, "Userdata"
                ).build().also {
                   INSTANCE= it
                }
            }
        }
    }
}