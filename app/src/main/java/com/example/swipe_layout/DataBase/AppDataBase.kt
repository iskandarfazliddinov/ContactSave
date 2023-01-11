package com.example.project_contact.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserData::class], version = 2)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        private var appDataBase: AppDataBase? = null

        @Synchronized

        fun getInstance(context: Context): AppDataBase {
            if (appDataBase == null) {

                appDataBase = Room.databaseBuilder(context, AppDataBase::class.java, "my_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return appDataBase!!
        }
    }
}