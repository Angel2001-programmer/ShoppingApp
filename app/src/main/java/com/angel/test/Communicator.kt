package com.angel.test

interface Communicator {
    fun passProductData(id: Int, image: String, name: String, desc: String, price: Double)
    fun passBasketInfo(id: Int, image: String, name: String, price: String)
}