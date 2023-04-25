package com.example.knihovna.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.knihovna.MyApplication
import com.example.knihovna.data.InfoBookAdapter

class AllBooksActivityViewModel (val app: MyApplication) : ViewModel() {
    var items : LiveData<kotlin.collections.List<InfoBookAdapter>> = app.itemDetailRepository.getAllInfoBook()
    var filteredItems : List<InfoBookAdapter> = ArrayList()

    class MyFactory(private val app: MyApplication) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AllBooksActivityViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AllBooksActivityViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun filterItems(str : String){
        filteredItems = app.itemDetailRepository.findStrInfoBooks(str)
    }
}