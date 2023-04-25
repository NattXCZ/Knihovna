package com.example.knihovna.repositories

import androidx.lifecycle.LiveData
import com.example.knihovna.data.*

class WishRepository (val database: MyDatabase) {
    fun insertWish(bookName: String, idAuthor: Long) : Wish {
        val item = Wish(book_name = bookName, id_author = idAuthor)
        database.myDao().insertWish(item)
        return item
    }

    fun updateWish(id: Long, bookName: String, idAuthor: Long) : Wish {
        val item = Wish(id, bookName, idAuthor)
        database.myDao().updateWish(item)
        return item
    }
    fun deleteWish(wish: Wish){
        database.myDao().deleteWish(wish)
    }

    fun getWishByNameAndAuthor(bookName: String, nameAuthor: String) : Wish {
        val item = database.myDao().getWishByNameAndAuthor(bookName,nameAuthor)
        return item
    }

    fun getAllWish() : LiveData<List<WishAdapter>> = database.myDao().getAllWish()

    fun getSearchWish(str: String) :List<WishAdapter> =database.myDao().findStrWish(str)

    fun getWishByName(str : String) : LiveData<List<WishAdapter>> = database.myDao().getWishByName(str)

    fun getWishInfoByID(id : Long) : WishAdapter = database.myDao().getWishInfoByID(id)

    fun getWishByID(id : Long) : Wish = database.myDao().getWishByID(id)

    fun existsWish(bookName : String, authorName: String) : Boolean = database.myDao().existsWish(bookName,authorName)
}