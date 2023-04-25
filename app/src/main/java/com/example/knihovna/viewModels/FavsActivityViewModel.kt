package com.example.knihovna.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.knihovna.MyApplication
import com.example.knihovna.data.InfoBookAdapter

class FavsActivityViewModel (val app: MyApplication) : ViewModel(){
    var items = app.itemDetailRepository.getAllInfoBookFav()
    var filteredItems : List<InfoBookAdapter> = ArrayList()

    class MyFactory(private val app: MyApplication) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavsActivityViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FavsActivityViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun filterItems(str : String){
        filteredItems = app.itemDetailRepository.findStrInfoBooksFav(str)
    }

}