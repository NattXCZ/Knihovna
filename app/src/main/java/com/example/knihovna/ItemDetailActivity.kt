package com.example.knihovna


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.knihovna.data.Book
import com.example.knihovna.databinding.ActivityItemDetailBinding
import com.example.knihovna.viewModels.ItemDetailActivityViewModel

class ItemDetailActivity : AppCompatActivity() {


    private val model: ItemDetailActivityViewModel by viewModels {
        ItemDetailActivityViewModel.MyFactory((application as MyApplication))
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setTitle(R.string.title_detail_of_book)



        val binding: ActivityItemDetailBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_item_detail)
        binding.model = model

        val bookName = intent.getStringExtra("book_name")
        val authorName = intent.getStringExtra("author_name")

        val bookFound = model.getBookByAuthorAndName(bookName.toString(),authorName.toString())
        val id =  bookFound.id_book

        model.setAllInfoBookByID(id)


        val observer0 = Observer<Book>{ item ->
            if(item != null) {
                val author = model.getAuthorByID(item.id_author)
                findViewById<TextView>(R.id.text_name).text = item.book_name

                findViewById<TextView>(R.id.text_author).text = getString(R.string.author_str) + author.author_name

                findViewById<TextView>(R.id.input_text_book_series).text = getString(R.string.series_str) + item.series
                findViewById<RatingBar>(R.id.rating_bar).rating = item.rating.toFloat()
                findViewById<TextView>(R.id.label_genre).text = item.genre
                findViewById<TextView>(R.id.date_year).text = getString(R.string.year_str) + item.year.toString()
                findViewById<TextView>(R.id.label_language).text = item.language
                findViewById<TextView>(R.id.num_pages).text = getString(R.string.pages_num_str) + item.page_num.toString()

                //heart button fav
                if(item.is_fav) findViewById<ImageButton>(R.id.button_fav).setImageResource(R.drawable.heart_icon_full)
                else findViewById<ImageButton>(R.id.button_fav).setImageResource(R.drawable.heart_icon)

            }
        }

        model.itemInfo.observe(this, observer0)


        //for closing activity after removing object
        model.itemRemoved.observe(this, Observer { removed ->
            if (removed) {
                // Vrátit se na předchozí aktivitu
                finish()
            }
        })

        //autocomplete
     //   setupAutoComplete()
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

}