package com.example.knihovna

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.knihovna.databinding.ActivityAddToWishlistBinding
import com.example.knihovna.viewModels.AddToWishlistActivityViewModel

class AddToWishlistActivity : AppCompatActivity() {
    private val model: AddToWishlistActivityViewModel by viewModels {
        AddToWishlistActivityViewModel.MyFactory((application as MyApplication))
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_wishlist)
        setTitle(R.string.title_add_wishlist)

        val binding: ActivityAddToWishlistBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_add_to_wishlist)
        binding.model = model

        //autofillingtext
        finishSetupAutoCompleteTextView()

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

    private fun finishSetupAutoCompleteTextView(){

        //after clicking "enter" in the AutoCompleteTextView moves to the next AutoCompleteTextView - helper function
        val inputTextName = findViewById<AutoCompleteTextView>(R.id.input_text_name)
        val inputTextAuthor = findViewById<AutoCompleteTextView>(R.id.input_text_author)


        setupAutoCompleteTextView(inputTextName, inputTextAuthor)
        setupAutoCompleteTextView(inputTextAuthor,findViewById<Button>(R.id.add_button) )

    }

    fun clickStoreSave(view: View) {

        val inputTextName = findViewById<AutoCompleteTextView>(R.id.input_text_name)
        val inputTextAuthor = findViewById<AutoCompleteTextView>(R.id.input_text_author)

        val nameBook = inputTextName.text.toString()
        val nameAuthor = inputTextAuthor.text.toString()

        if (nameBook.isEmpty() || inputTextAuthor.text.isEmpty() ) {
            Toast.makeText(this, "Vyplňte název a autora", Toast.LENGTH_SHORT).show()
            return
        }
        if(model.existsWish(nameBook,nameAuthor)){
            Toast.makeText(this, "Tato kniha již v seznamu přání existuje", Toast.LENGTH_SHORT).show()
        }
        else{
            model.insertWish(nameBook, nameAuthor)
        }
        finish()
    }
}