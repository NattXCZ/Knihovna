package com.example.knihovna.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.knihovna.MyApplication
import com.example.knihovna.data.Wish

class AddToWishlistActivityViewModel (val app: MyApplication) : ViewModel() {

    class MyFactory(private val app: MyApplication) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddToWishlistActivityViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddToWishlistActivityViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun insertWish(bookName : String, authorName : String){

        if(!app.authorRepository.existsAuthor(authorName)){
            app.authorRepository.insertAuthor(authorName)
        }
        val idAuthor = app.authorRepository.getAuthorByName(authorName).id_author
        app.wishRepository.insertWish(bookName,idAuthor)
    }

    fun updateWish(id: Long, bookName: String, idAuthor: Long){
        app.wishRepository.updateWish(id,bookName, idAuthor)
    }


    fun deleteWish(id: Long, bookName: String, idAuthor: Long){
        val wish = Wish(id, bookName, idAuthor)
        app.wishRepository.deleteWish(wish)
    }

    fun existsWish(name : String, author : String) : Boolean{
        return app.wishRepository.existsWish(name,author)
    }





}