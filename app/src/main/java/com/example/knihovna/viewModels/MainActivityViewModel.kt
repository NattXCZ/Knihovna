package com.example.knihovna.viewModels

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.knihovna.*

class MainActivityViewModel (val app: MyApplication) : ViewModel() {

     class MyFactory(private val app: MyApplication) : ViewModelProvider.Factory {
         override fun <T : ViewModel> create(modelClass: Class<T>): T {
             if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                 @Suppress("UNCHECKED_CAST")
                 return MainActivityViewModel(app) as T
             }
             throw IllegalArgumentException("Unknown ViewModel class")
         }
     }

    fun AllBClick(view: View) {
        val intent = Intent(view.context, AllBooksActivity::class.java)
        view.context.startActivity(intent)
    }

    fun AddAllBClick(view: View) {
        val intent = Intent(view.context, AddToAllActivity::class.java)
        intent.putExtra("is_edited", 0)
        view.context.startActivity(intent)
    }

    fun WishlistClick(view: View) {
        val intent = Intent(view.context, WishlistActivity::class.java)
        view.context.startActivity(intent)
    }
    fun AddWishlistBClick(view: View) {
        val intent = Intent(view.context, AddToWishlistActivity::class.java)
        intent.putExtra("is_edited", 0)
        view.context.startActivity(intent)
    }

    fun FavsBClick(view: View) {
        val intent = Intent(view.context, FavsActivity::class.java)
        view.context.startActivity(intent)
    }

}

