package com.angel.test

//Filter the data to retrieve data that you want in this application.
class value : ArrayList<valueItem>()

data class valueItem(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)

data class Rating(
    val count: Int,
    val rate: Double
)
