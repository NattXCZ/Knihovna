package com.example.knihovna.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

/*
books table:
id_book, book_name, id_author, rating, series, genre, language, page_num, is_fav
 */
@Entity(tableName = "books",foreignKeys = [ForeignKey(entity = Author::class,
    parentColumns = ["id_author"],
    childColumns = ["id_author"])],
    indices = [Index("id_author")]
)
data class Book(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_book") val id_book :  Long = 0,
                @ColumnInfo(name = "book_name") val book_name : String,
                @ColumnInfo(name = "id_author") val id_author : Long, //foreignKey
                @ColumnInfo(name = "rating") val rating : Long,
                @ColumnInfo(name = "series") val series : String,
                @ColumnInfo(name = "genre") val genre : String,
                @ColumnInfo(name = "year") val year : Long,
                @ColumnInfo(name = "language") val language : String,
                @ColumnInfo(name = "page_num") val page_num : Long,
                @ColumnInfo(name = "is_fav") val is_fav : Boolean)

/*
wishlist table:
id_book, book_name, id_author
 */
@Entity(tableName = "wishlist",foreignKeys = [ForeignKey(entity = Author::class,
    parentColumns = ["id_author"],
    childColumns = ["id_author"])],
    indices = [Index("id_author")]
)
data class Wish(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_book") val id_book :  Long = 0,
                @ColumnInfo(name = "book_name") val book_name : String,
                @ColumnInfo(name = "id_author") val id_author : Long) //foreignKey
/*
authors table
id_author, author_name, author_surname, author_middle_name
*/
@Entity(tableName = "authors")
data class Author(@PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id_author") val id_author: Long = 0,
                   @ColumnInfo(name = "author_name") val author_name : String)



//for a recyclerView

data class InfoBookAdapter(val id_book : Long, val book_name : String,val id_author : Long, val author_name : String, val rating : Long, val series : String, val genre : String, val year : Long, val language : String, val page_num : Long,  val is_fav : Boolean)

data class AuthorAdapter(val id_author : Long, val author_name : String)

data class WishAdapter(val id_book :  Long, val book_name : String , val id_author : Long, val author_name : String)
