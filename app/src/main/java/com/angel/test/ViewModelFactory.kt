package com.angel.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.angel.test.ui.home.HomeViewModel

class ViewModelFactory constructor(private val repository: MainRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if
                (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}