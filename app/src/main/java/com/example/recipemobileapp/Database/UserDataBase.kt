package com.example.recipemobileapp.Database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class Userdatabase : RoomDatabase() {
    abstract fun dataDao(): MealDao
    companion object{
        @Volatile
        private var INSTANCE:Userdatabase?= null
        fun getInstance(context: Context): Userdatabase{
            return INSTANCE?: synchronized(this){
                INSTANCE?: Room.databaseBuilder(
                    context.applicationContext,Userdatabase::class.java, "Userdata"
                ).build().also {
                    INSTANCE= it
                }
            }
        }
    }
}