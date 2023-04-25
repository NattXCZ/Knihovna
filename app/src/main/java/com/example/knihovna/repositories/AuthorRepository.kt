package com.example.knihovna.repositories

import androidx.lifecycle.LiveData
import com.example.knihovna.data.Author
import com.example.knihovna.data.AuthorAdapter
import com.example.knihovna.data.MyDatabase

class AuthorRepository (val database : MyDatabase) {

    fun insertAuthor(authorName: String ) : Author {
        //creates key
        val item = Author(author_name = authorName)

        database.myDao().insertAuthor(item)
        return item
    }

    fun updateItem(id: Long, authorName: String ) : Author {
        val item = Author(id,authorName)
        database.myDao().updateAuthor(item)
        return item
    }

    fun deleteAuthor(author: Author){
        database.myDao().deleteAuthor(author)
    }

    fun getAllAuthorNames() : Array<String> = database.myDao().getAllAuthorNames()

    fun getAllAuthors() : LiveData<List<AuthorAdapter>> = database.myDao().getAllAuthors()

    fun getAuthorByID(id : Long) : Author = database.myDao().getAuthorByID(id)

    fun getAuthorByName(string: String) : Author = database.myDao().getAuthorByName(string)

    fun existsAuthor(name : String) : Boolean = database.myDao().existsAuthor(name)

}