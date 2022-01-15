package com.angel.test

class MainRepository constructor(private val retrofitService: RetrofitService) {
    suspend fun getAllMovies() = retrofitService.getAllMovies()
}