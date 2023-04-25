package com.example.knihovna.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.knihovna.MyApplication
import com.example.knihovna.data.Author
import com.example.knihovna.data.Book


class AddToAllActivityViewModel(val app: MyApplication) : ViewModel() {
    lateinit var itemInfo : LiveData<Book>

    class MyFactory(private val app: MyApplication) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddToAllActivityViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddToAllActivityViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
    fun insertBook( book_name : String,  authorName : String,  rating : Long,  series : String,  genre : String,year : Long, language : String,  page_num : Long,   is_fav : Boolean){

        if(!app.authorRepository.existsAuthor(authorName)){
            app.authorRepository.insertAuthor(authorName)
        }
        val idAuthor = app.authorRepository.getAuthorByName(authorName).id_author

        app.itemDetailRepository.insertBook(book_name,idAuthor,rating,series,genre,year,language,page_num,is_fav)
    }

    fun updateBook(id_book : Long,  book_name : String,  author_id : Long,  rating : Long,  series : String,  genre : String,year : Long, language : String,  page_num : Long,    is_fav : Boolean){
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

    fun isExistBook(bookName: String, authorName: String) : Boolean{
        return app.itemDetailRepository.existsBook(bookName,authorName)
    }

    fun getBookByID(id : Long) : Book{
        return app.itemDetailRepository.getBookByID(id)
    }

    fun setAllInfoBookByID(newId : Long){
        itemInfo = app.itemDetailRepository.getAllInfoBookByID(newId)
    }

    fun getAuthorByID(id : Long) : Author {
        return app.itemDetailRepository.getAuthorByID(id)
    }

}