package com.example.knihovna.viewModels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.knihovna.MyApplication
import com.example.knihovna.data.Author
import com.example.knihovna.data.Book


class ItemDetailActivityViewModel(val app: MyApplication) : ViewModel()  {
    lateinit var itemInfo : LiveData<Book>

    //for closing activity after removing item
    private val _itemRemoved = MutableLiveData<Boolean>()
    val itemRemoved: LiveData<Boolean>
        get() = _itemRemoved
    //

    class MyFactory(private val app: MyApplication) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ItemDetailActivityViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ItemDetailActivityViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


    fun getBookByID(id : Long) :  LiveData<Book> {   ///umeneno z infoadapter
        return app.itemDetailRepository.getAllInfoBookByID(id)
    }

    fun updateBook(id_book : Long,  book_name : String,  author_id : Long,  rating : Long,  series : String,  genre : String,year : Long, language : String,  page_num : Long,   is_fav : Boolean){
        app.itemDetailRepository.updateBook(id_book,book_name,author_id,rating,series,genre,year,language,page_num,is_fav)
    }

    fun deleteBook(id: Long){
        val book = app.itemDetailRepository.getBookByID(id)
        val authorId = book.id_author
        app.itemDetailRepository.deleteBook(book)
        if(app.itemDetailRepository.getBooksByAuthorID(authorId).value == null){
            val author = app.authorRepository.getAuthorByID(authorId)
            app.authorRepository.deleteAuthor(author)
        }
    }

    fun getAllInfoBookByID(id : Long) : LiveData<Book> {
        return app.itemDetailRepository.getAllInfoBookByID(id)
    }

    fun setAllInfoBookByID(newId : Long){
        itemInfo = app.itemDetailRepository.getAllInfoBookByID(newId)
    }

    fun favClick(view: View) {

        if(itemInfo.value!!.is_fav){
            app.itemDetailRepository.updateBook(
                itemInfo.value!!.id_book,
                itemInfo.value!!.book_name,
                itemInfo.value!!.id_author,
                itemInfo.value!!.rating,
                itemInfo.value!!.series,
                itemInfo.value!!.genre,
                itemInfo.value!!.year,
                itemInfo.value!!.language,
                itemInfo.value!!.page_num,
                false)
        }
        else {
            app.itemDetailRepository.updateBook(
                itemInfo.value!!.id_book,
                itemInfo.value!!.book_name,
                itemInfo.value!!.id_author,
                itemInfo.value!!.rating,
                itemInfo.value!!.series,
                itemInfo.value!!.genre,
                itemInfo.value!!.year,
                itemInfo.value!!.language,
                itemInfo.value!!.page_num,
                true
            )
        }


    }

    fun deleteClick(view: View){
        app.itemDetailRepository.deleteBook(itemInfo.value!!)
        _itemRemoved.value = true

    }

    fun getAuthorByID(id : Long) : Author {
        return app.itemDetailRepository.getAuthorByID(id)
    }

    fun getBookByAuthorAndName(bookName: String, authorName: String): Book {
        return app.itemDetailRepository.getBookByAuthorAndName(bookName,authorName )
    }

}