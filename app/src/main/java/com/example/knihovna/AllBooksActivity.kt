package com.example.knihovna

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.DataBindingUtil
import com.example.knihovna.adapters.AdapterBooks
import com.example.knihovna.data.InfoBookAdapter
import com.example.knihovna.data.MyDatabase
import com.example.knihovna.databinding.ActivityAllBooksBinding
import com.example.knihovna.viewModels.AllBooksActivityViewModel

class AllBooksActivity : AppCompatActivity() {

    private val model: AllBooksActivityViewModel by viewModels {
        AllBooksActivityViewModel.MyFactory((application as MyApplication))
    }


    private var recyclerView: RecyclerView? = null
    private var adapter: AdapterBooks? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_books)
        setTitle(R.string.title_your_books)

        ///

        val binding : ActivityAllBooksBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_books)
        binding.model = model

        adapter = AdapterBooks(mutableListOf<InfoBookAdapter>(), ::nextActivity, 0)


        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView?.adapter = adapter


        val find = findViewById<AutoCompleteTextView>(R.id.autocomplete_text)
        val suggestions : Array<String> = getSuggestions(this)


        ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, suggestions).also{
                adapter -> find.setAdapter(adapter)
        }

        find.addTextChangedListener {
            if(it.toString() == ""){
                model.items.observe(this) { adapter?.setItems(it)}
            }
            else {
                model.filterItems(it.toString())
                adapter?.setItems(model.filteredItems)
            }
        }

        model.items.observe(this) {
            adapter?.setItems(it)
            find.setText("")
        }
    }

    private fun nextActivity(id_book: Long){}

    private fun getSuggestions(context: Context): Array<String> {
        val dao = MyDatabase.getDatabase(context).myDao()
        val bookNames = dao.getAllBookNames()
        val authorNames = dao.getAllAuthorNames()
        return bookNames + authorNames
    }


}