package com.example.knihovna.repositories

import androidx.lifecycle.LiveData
import com.example.knihovna.data.Author
import com.example.knihovna.data.Book
import com.example.knihovna.data.InfoBookAdapter
import com.example.knihovna.data.MyDatabase


class ItemDetailRepository(val database : MyDatabase) {

    fun insertBook( book_name : String,  author_id : Long,  rating : Long,  series : String,  genre : String,year : Long, language : String,  page_num : Long,   is_fav : Boolean) : Book {

        val item = Book(book_name = book_name, id_author = author_id, rating = rating, series = series, genre = genre,year = year, language = language, page_num = page_num,is_fav = is_fav)

        database.myDao().insertBook(item)
        return item
    }

    fun updateBook( id_book : Long,  book_name : String,  author_id : Long,  rating : Long,  series : String,  genre : String,year : Long, language : String,  page_num : Long,  is_fav : Boolean) : Book {
        val item = Book(id_book,book_name,author_id,rating,series,genre,year,language,page_num,is_fav)

        database.myDao().updateBook(item)
        return item
    }

    fun deleteBook(item: Book){
        database.myDao().deleteBook(item)
    }


    //Books
    fun findStrInfoBooks(str : String) : kotlin.collections.List<InfoBookAdapter> = database.myDao().findStrInfoBooks(str)

    fun getAllInfoBook() : LiveData<kotlin.collections.List<InfoBookAdapter>> = database.myDao().getAllInfoBook()

    fun getBookByID(id :Long) : Book = database.myDao().getBookByID(id)

    fun existsBook(bookName : String, authorName: String) : Boolean = database.myDao().existsBook(bookName,authorName)

    fun getBooksByAuthorID(id : Long) : LiveData<kotlin.collections.List<Book>> = database.myDao().getBooksByAuthorID(id)

    fun getAllInfoBookByID(id: Long) :  LiveData<Book> = database.myDao().getAllInfoBookByID(id)

    fun getAuthorByID(id: Long) : Author = database.myDao().getAuthorByID(id)


    //FavBooks

    fun findStrInfoBooksFav(str : String) : kotlin.collections.List<InfoBookAdapter> = database.myDao().findStrInfoBooksFav(str)

    fun getAllInfoBookFav() : LiveData<kotlin.collections.List<InfoBookAdapter>> =database.myDao().getAllInfoBookFav()

    fun existsFavBook(bookName : String, authorName: String) : Boolean = database.myDao().existsFavBook(bookName,authorName)

    fun getFavBookByID(id :Long) : Book = database.myDao().getFavBookByID(id)

    fun getAllInfoBookFavByID(id: Long) : LiveData<kotlin.collections.List<InfoBookAdapter>> = database.myDao().getAllInfoBookFavByID(id)
    fun getBookByAuthorAndName(bookName: String, authorName: String): Book  = database.myDao().getBookByAuthorAndName(bookName, authorName)


}