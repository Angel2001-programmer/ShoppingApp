package com.angel.test.ui.home

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angel.test.PhotoAdapter
import com.angel.test.api.MainRepository
import com.angel.test.fragments.MoreDetailsFragment
import com.angel.test.valueItem
import kotlinx.coroutines.*

class HomeViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val text: LiveData<String> = _text

    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<valueItem>>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllMovies() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = mainRepository.getAllMovies()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    movieList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun saveItemClicks(adapter: PhotoAdapter){
        adapter.setOnItemClickListener(object : PhotoAdapter.onitemClickListener {
            override fun onItemClick(position: Int) {
                //Get position of objects.
                val id = adapter.movieList.get(position).id
                val image = adapter.movieList.get(position).image
                val name = adapter.movieList.get(position).title
                val desc = adapter.movieList.get(position).description
                val price = adapter.movieList.get(position).price

                Log.d(this@HomeViewModel.toString(), "onItemClick: " + id + "" + image + "" + name + "" + desc + "" + price)
            }
        })
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}