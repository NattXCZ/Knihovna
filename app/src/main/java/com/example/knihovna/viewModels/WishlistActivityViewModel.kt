package com.example.knihovna.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.knihovna.MyApplication
import com.example.knihovna.data.WishAdapter

class WishlistActivityViewModel(val app: MyApplication) : ViewModel() {
    var items = app.wishRepository.getAllWish()
    var filteredItems : List<WishAdapter> = ArrayList()

    class MyFactory(private val app: MyApplication) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WishlistActivityViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WishlistActivityViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun filterItems(str : String){
        filteredItems = app.wishRepository.getSearchWish(str)
    }

    fun isExistBook(bookName: String, authorName: String) : Boolean{
        return app.wishRepository.existsWish(bookName,authorName)
    }

    fun deleteWish(bookName: String, nameAuthor: String){
        val wish = app.wishRepository.getWishByNameAndAuthor(bookName,nameAuthor)
        app.wishRepository.deleteWish(wish)
    }


}