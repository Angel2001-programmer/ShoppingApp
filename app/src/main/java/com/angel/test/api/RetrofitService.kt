package com.angel.test.api

import com.angel.test.valueItem
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("products")
    suspend fun getAllMovies(): Response<List<valueItem>>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://fakestoreapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService =
                    retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}