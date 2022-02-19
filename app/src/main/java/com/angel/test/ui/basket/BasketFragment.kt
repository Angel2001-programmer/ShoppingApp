package com.angel.test.ui.basket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.angel.test.adapters.BasketAdapter
import com.angel.test.databinding.FragmentBasketBinding
import com.angel.test.models.Product
import com.google.firebase.database.*

class BasketFragment : Fragment() {

    private lateinit var basketViewModel: BasketViewModel
    private lateinit var db: DatabaseReference
    private lateinit var clientList: ArrayList<Product>
    private lateinit var priceList: ArrayList<String>
    private lateinit var newList: List<Double>

    private var _binding: FragmentBasketBinding? = null
    private val TAG = "SlideshowFragment"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)
        val root: View = binding.root
//        basketViewModel = ViewModelProvider(
//            this).get(BasketViewModel::class.java)

        binding.recyclerView1.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView1.hasFixedSize()
        clientList = arrayListOf<Product>()
        priceList = arrayListOf<String>()

        binding.button2.setOnClickListener {
            Log.d(TAG, "onCreateView: Purchase is successful")
        }
        getAllBasket()
        binding.button2.text = "Total"
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getAllBasket() {
        db = FirebaseDatabase.getInstance().getReference("Products")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (productSnapshot in snapshot.children) {
                        val product = productSnapshot.getValue(Product::class.java)
                        clientList.add(product!!)
                        priceList.add(product.price!!)
                    }
                    binding.recyclerView1.adapter = BasketAdapter(clientList)

                    //Loop through array to find each price of item. I have signed value to
                    // itself to save resources on memory too many unnecessary variables will
                    // cause the app to be very slow for users.
                    // remove £ sign to be able to convert string successfully as double.
                    for (i in priceList) {
                        var value = i
                        if (value.contains("£")) {
                            value = value.replace("£", "")
                            val number = value.toDouble()
                            newList = listOf<Double>(number)
                        }
                        println(newList.sum())
                    }
//                    println(priceList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: $error")
            }
        })
    }
}