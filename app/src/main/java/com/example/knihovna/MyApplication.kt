package com.example.knihovna

import android.app.Application
import com.example.knihovna.data.MyDatabase
import com.example.knihovna.repositories.AuthorRepository
import com.example.knihovna.repositories.ItemDetailRepository
import com.example.knihovna.repositories.WishRepository

class MyApplication : Application() {

    val database by lazy{ MyDatabase.getDatabase(this)}
    val itemDetailRepository by lazy { ItemDetailRepository(database) }
    val authorRepository by lazy { AuthorRepository(database) }
    val wishRepository by lazy { WishRepository(database) }
}
