package com.angel.test.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.angel.test.adapters.BasketAdapter
import com.angel.test.models.Product
import com.google.firebase.database.*
import kotlinx.coroutines.*

class SlideshowViewModel : ViewModel() {
    private lateinit var dataRef: DatabaseReference
    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text
    val clientArray = MutableLiveData<Product>()
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
}