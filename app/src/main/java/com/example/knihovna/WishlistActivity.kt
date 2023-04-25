package com.example.knihovna

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knihovna.adapters.AdapterWishes
import com.example.knihovna.data.MyDatabase
import com.example.knihovna.data.WishAdapter
import com.example.knihovna.databinding.ActivityWishlistBinding
import com.example.knihovna.viewModels.WishlistActivityViewModel

class WishlistActivity : AppCompatActivity() {


    private val model: WishlistActivityViewModel by viewModels {
        WishlistActivityViewModel.MyFactory((application as MyApplication))
    }

    private var recyclerView: RecyclerView? = null
    private var adapter: AdapterWishes? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)
        setTitle(R.string.title_wish_list)

        val binding : ActivityWishlistBinding = DataBindingUtil.setContentView(this, R.layout.activity_wishlist)
        binding.model = model


        adapter = AdapterWishes(mutableListOf<WishAdapter>(), ::nextActivity, 0, model)


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

    private fun nextActivity(id: Long){}

    private fun getSuggestions(context: Context): Array<String> {
        val dao = MyDatabase.getDatabase(context).myDao()
        val bookNames = dao.getAllWishNames()
        val authorNames = dao.getAllAuthorNames()
        return bookNames + authorNames
    }


}