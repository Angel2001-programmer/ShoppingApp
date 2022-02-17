package com.angel.test.api

class MainRepository constructor(private val retrofitService: RetrofitService) {
    suspend fun getAllMovies() = retrofitService.getAllMovies()
}