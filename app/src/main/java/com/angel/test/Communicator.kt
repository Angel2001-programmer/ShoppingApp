package com.angel.test

import android.widget.TextView

interface Communicator {
        fun passProductData(image: String, name: String, desc: String, price: Double)
        fun passBasketInfo(image: String, name: String, price: String)
}