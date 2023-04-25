package com.example.knihovna.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book::class, Wish::class, Author::class], version = 3)
abstract class MyDatabase : RoomDatabase(){

    abstract fun myDao() : MyDAO

    companion object{
        private var instance: MyDatabase? = null
        fun getDatabase(context: Context) : MyDatabase {
            if (instance != null) {
                return instance as MyDatabase
            } else {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "MojeDatabase"
                ).allowMainThreadQueries().build()
                return instance as MyDatabase
            }
        }
    }
}