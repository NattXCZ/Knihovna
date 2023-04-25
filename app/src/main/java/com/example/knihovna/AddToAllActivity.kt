package com.example.knihovna

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.knihovna.data.Book
import com.example.knihovna.databinding.ActivityAddToAllBinding
import com.example.knihovna.viewModels.AddToAllActivityViewModel

class AddToAllActivity : AppCompatActivity() {

    val regexYear = "^\\d{4}$".toRegex()



    private val model: AddToAllActivityViewModel by viewModels {
        AddToAllActivityViewModel.MyFactory((application as MyApplication))
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_all)
        setTitle(R.string.title_add)


        val binding: ActivityAddToAllBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_add_to_all)
        binding.model = model

        if(intent.getStringExtra("is_edited")?.toInt() == 1){

            val bookFound = model.getBookByID(intent.getStringExtra("id_book")!!.toLong())
             val id =  bookFound.id_book

            model.setAllInfoBookByID(id)


            val observer0 = Observer<Book>{ item ->
                if(item != null) {
                    val author = model.getAuthorByID(item.id_author)


                    findViewById<AutoCompleteTextView>(R.id.input_text_name).setText(item.book_name)
                    findViewById<AutoCompleteTextView>(R.id.input_text_author).setText(author.author_name)
                    findViewById<AutoCompleteTextView>(R.id.input_text_book_series).setText(item.series)
                    findViewById<RatingBar>(R.id.rating_bar).rating = item.rating.toFloat()

                    findViewById<EditText>(R.id.input_year).setText(item.year.toString())

                    findViewById<EditText>(R.id.input_pages).setText(item.page_num.toString())
                    findViewById<CheckBox>(R.id.fav_checkbox).isChecked = item.is_fav

                }
            }

            model.itemInfo.observe(this, observer0)


        }

        setupAutoComplete()

    }

    //function
    //after clicking "enter" in the AutoCompleteTextView moves to the next AutoCompleteTextView
    private fun setupAutoCompleteTextView(acTextView: AutoCompleteTextView, nextView: View) {
        acTextView.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                nextView.requestFocus()
                return@OnKeyListener true
            }
            false
        })
    }

    private fun setupAutoComplete(){

        //after clicking "enter" in the AutoCompleteTextView moves to the next AutoCompleteTextView - helper function
        val inputTextName = findViewById<AutoCompleteTextView>(R.id.input_text_name)
        val inputTextAuthor = findViewById<AutoCompleteTextView>(R.id.input_text_author)
        val inputBookSeries = findViewById<AutoCompleteTextView>(R.id.input_text_book_series)
        val inputYear = findViewById<EditText>(R.id.input_year)


        setupAutoCompleteTextView(inputTextName, inputTextAuthor)
        setupAutoCompleteTextView(inputTextAuthor, inputBookSeries)
        setupAutoCompleteTextView(inputBookSeries, inputYear)
    }


    fun clickStoreSave(view: View) {
        val name = findViewById<AutoCompleteTextView>(R.id.input_text_name).text
        val author = findViewById<AutoCompleteTextView>(R.id.input_text_author).text
        val bookSeries = findViewById<AutoCompleteTextView>(R.id.input_text_book_series).text
        val rating = findViewById<RatingBar>(R.id.rating_bar).rating
        val genre = findViewById<Spinner>(R.id.spinner_genre).selectedItem.toString()
        val year = findViewById<EditText>(R.id.input_year).text //num
        val language = findViewById<Spinner>(R.id.spinner_language).selectedItem.toString()
        val pages = findViewById<EditText>(R.id.input_pages).text ///num
        val is_fav = findViewById<CheckBox>(R.id.fav_checkbox).isChecked

        if(isValidInput(name, author, year, bookSeries,pages) && !model.isExistBook(name.toString(),author.toString())){

            model.insertBook(name.toString(),  author.toString() ,
                rating.toLong(),
                bookSeries.toString(),  genre ,year.toString().toLong() , language ,  pages.toString().toLong() ,   is_fav)

            finish()
        }
        else {
            makeToastText("Tato kniha již v seznamu existuje")
        }

    }

    private fun isValidInput(name: Editable, author: Editable, year: Editable, bookSeries: Editable, pages: Editable): Boolean {
        return when {
            name.isEmpty() -> {
                makeToastText("Název knihy je povinný")
                false
            }
            author.isEmpty() -> {
                makeToastText("Autor je povinný")
                false
            }
            year.isEmpty() -> {
                makeToastText("Rok vydání je povinný")
                false
            }
            bookSeries.isEmpty() -> {
                makeToastText("Knižní série je povinná")
                false
            }
            !regexYear.matches(year) -> {
                makeToastText("Rok vydání musí být ve formátu yyyy")
                false
            }
            pages.isEmpty() -> {
                makeToastText("Počet stran je povinný")
                false
            }
            else -> true
        }
    }

    private fun makeToastText(string: String){
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()

    }

}