package com.example.knihovna.data


import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.room.*



@Dao
interface MyDAO {

    /*
    BookAdapter
    -all info about the book:
    (val id_book : Long, val book_name : String, val id_author : Long,val author_name : String, val rating : Long, val series : String, val genre : String,
     val year : Long, val language : String, val page_num : Long, val is_read : Boolean, val is_owned : Boolean, val is_fav : Boolean)
     */


    @Query("SELECT * FROM books WHERE id_book = :id")
    fun getAllInfoBookByID(id: Long) : LiveData<Book> //LiveData<kotlin.collections.List<InfoBookAdapter>> a pridat author_name




    @Query("SELECT DISTINCT id_book, book_name, w.id_author,  author_name, rating, series, genre,year,  language, page_num,is_fav FROM books w INNER JOIN authors a ON w.id_author = a.id_author WHERE w.id_book = :id AND w.is_fav = 1")
    fun getAllInfoBookFavByID(id: Long) : LiveData<kotlin.collections.List<InfoBookAdapter>>

    @Query("SELECT DISTINCT id_book, book_name, w.id_author, author_name, rating, series, genre,year, language, page_num, is_fav FROM books w INNER JOIN authors a ON w.id_author = a.id_author")
    fun getAllInfoBook() : LiveData<kotlin.collections.List<InfoBookAdapter>>

    @Query("SELECT DISTINCT id_book, book_name, w.id_author,  author_name, rating, series, genre,year,  language, page_num,  is_fav FROM books w INNER JOIN authors a ON w.id_author = a.id_author AND w.is_fav = 1")
    fun getAllInfoBookFav() : LiveData<kotlin.collections.List<InfoBookAdapter>>



    /*
    Book
     */
    @Query("SELECT DISTINCT id_book, book_name, w.id_author, rating, series, genre,year, language, page_num, is_fav FROM books w INNER JOIN authors a ON w.id_author = a.id_author WHERE w.id_author = :id")
    fun getBooksByAuthorID(id: Long) :  LiveData<List<Book>>  //bud zmenit na InfoBookAdapter nebo misto name dat id

    @Query("SELECT * FROM books WHERE id_book = :id")
    fun getBookByID(id: Long) : Book

    @Query("SELECT * FROM books WHERE id_book = :id AND is_fav = 1")
    fun getFavBookByID(id: Long) : Book

    @Query("SELECT EXISTS (SELECT 1 FROM (SELECT * FROM books INNER JOIN authors ON books.id_author = authors.id_author) AS t WHERE t.book_name = :nameBook AND t.author_name = :nameAuthor)")
    fun existsBook(nameBook: String, nameAuthor : String) : Boolean

    @Query("SELECT EXISTS (SELECT 1 FROM (SELECT * FROM books INNER JOIN authors ON books.id_author = authors.id_author) AS t WHERE t.book_name = :nameBook AND t.author_name = :nameAuthor AND t.is_fav = 1)")
    fun existsFavBook(nameBook: String, nameAuthor : String) : Boolean

    @Query("SELECT DISTINCT id_book, book_name, w.id_author, rating, series, genre,year, language, page_num, is_fav FROM books w INNER JOIN authors a ON w.id_author = a.id_author WHERE author_name = :authorName AND book_name = :bookName")
    fun getBookByAuthorAndName(bookName: String, authorName: String) :  Book
    /*
    AuthorAdapter
    (val id_author : Long, val author_name : String)
     */

    @Query("SELECT *  FROM authors")
    fun getAllAuthors() : LiveData<kotlin.collections.List<AuthorAdapter>>


    /*
    Author
     */
    @Query("SELECT * FROM authors WHERE id_author = :id")
    fun getAuthorByID(id: Long) : Author

    @Query("SELECT * FROM authors WHERE author_name = :str")
    fun getAuthorByName(str: String) : Author

    @Query("SELECT EXISTS (SELECT 1 FROM authors WHERE author_name = :nameAuthor)")
    fun existsAuthor(nameAuthor : String) : Boolean



    /*
    WishAdapter
    (val id_book :  Long, val book_name : String , val id_author : Long, val author_name : String)
     */
    @Query("SELECT DISTINCT id_book, book_name,w.id_author,  author_name  FROM wishlist w INNER JOIN authors a ON w.id_author = a.id_author ")
    fun getAllWish() : LiveData<kotlin.collections.List<WishAdapter>>

    @Query("SELECT id_book, wishlist.book_name, wishlist.id_author, authors.author_name FROM wishlist INNER JOIN authors ON wishlist.id_author = authors.id_author WHERE wishlist.book_name = :str")
    fun getWishByName(str: String) : LiveData<kotlin.collections.List<WishAdapter>>

    @Query("SELECT id_book, wishlist.book_name,wishlist.id_author, authors.author_name FROM wishlist INNER JOIN authors ON wishlist.id_author = authors.id_author WHERE wishlist.id_book = :id")
    fun getWishInfoByID(id : Long) : WishAdapter


    /*
    wish
     */

    @Query("SELECT DISTINCT id_book, book_name,w.id_author  FROM wishlist w INNER JOIN authors a ON w.id_author = a.id_author WHERE w.book_name = :bookName AND a.author_name = :nameAuthor")
    fun getWishByNameAndAuthor(bookName: String, nameAuthor: String) : Wish


    @Query("SELECT id_book, book_name, id_author FROM wishlist WHERE wishlist.id_book = :id")
    fun getWishByID(id: Long) : Wish

    @Query("SELECT EXISTS (SELECT 1 FROM (SELECT * FROM wishlist INNER JOIN authors ON wishlist.id_author = authors.id_author) AS t WHERE t.book_name = :nameBook AND t.author_name = :nameAuthor)")
    fun existsWish(nameBook: String, nameAuthor : String) : Boolean

    /*
    others
     */

    //names od the books/authors/wishes from the database (for example for the method "getSuggestions")
    @Query("SELECT book_name FROM books")
    fun getAllBookNames() : Array<String>

    @Query("SELECT book_name FROM wishlist")
    fun getAllWishNames() : Array<String>

    @Query("SELECT author_name FROM authors")
    fun getAllAuthorNames() : Array<String>


    //search books by series, name or author
    @Query("SELECT DISTINCT id_book, book_name, w.id_author, author_name, rating, series, genre,year,  language, page_num, is_fav  FROM books w INNER JOIN authors a ON w.id_author = a.id_author WHERE (a.author_name = :str OR (w.book_name = :str OR w.series = :str))")
    fun findStrInfoBooks(str : String) : kotlin.collections.List<InfoBookAdapter>

    @Query("SELECT DISTINCT id_book, book_name,w.id_author, author_name, rating, series, genre,year,  language, page_num,  is_fav  FROM books w INNER JOIN authors a ON w.id_author = a.id_author WHERE (a.author_name = :str OR (w.book_name = :str OR w.series = :str)) AND is_fav = 1")
    fun findStrInfoBooksFav(str : String) : kotlin.collections.List<InfoBookAdapter>

    @Query("SELECT DISTINCT id_book, book_name,w.id_author,  author_name  FROM wishlist w INNER JOIN authors a ON w.id_author = a.id_author WHERE (a.author_name = :str OR w.book_name = :str )")
    fun findStrWish(str : String) : kotlin.collections.List<WishAdapter>


    //insertion
    @Insert
    fun insertBook (book : Book)

    @Insert
    fun insertAuthor (author : Author)

    @Insert
    fun insertWish (wish: Wish)

    //updating
    @Update
    fun updateBook(book : Book)

    @Update
    fun updateAuthor(author: Author)

    @Update
    fun updateWish(wish: Wish)


    //deleting
    @Delete
    fun deleteBook(book: Book)

    @Delete
    fun deleteAuthor(author : Author)

    @Delete
    fun deleteWish(wish: Wish)
}