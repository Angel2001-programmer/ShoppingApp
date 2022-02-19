package com.angel.test.ui.basket

import SwipeToDeleteCallback
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.angel.test.adapters.BasketAdapter
import com.angel.test.databinding.FragmentBasketBinding
import com.angel.test.fragments.name
import com.angel.test.models.Product
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*

class BasketFragment : Fragment() {

    private lateinit var basketViewModel: BasketViewModel
    private lateinit var db: DatabaseReference
    private lateinit var clientList: ArrayList<Product>
    private lateinit var priceList: ArrayList<String>
    private lateinit var newList: ArrayList<Double>

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

        // Setup RecyclerView
        binding.recyclerView1.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView1.hasFixedSize()
        clientList = arrayListOf()
        priceList = arrayListOf()
        newList = arrayListOf()

        // Button functionality
        binding.button2.setOnClickListener {
            Snackbar.make(it, "Purchase is successful", Snackbar.LENGTH_LONG).show()
            clientList.clear()
            db.removeValue()
            binding.recyclerView1.adapter?.notifyDataSetChanged()
            Log.d(TAG, "onCreateView: Purchase is successful")
        }
        //Call function
        getAllBasket()

        //get position of item that is being deleted.
        //allow the recyclerview to know the item has been removed from the list.
        //allow user to undo the change in case it was a mistake.
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                clientList.removeAt(position)
                binding.recyclerView1.adapter?.notifyItemRemoved(position)

                Snackbar.make(viewHolder.itemView, "item removed.",
                    Snackbar.LENGTH_LONG).setAction("UNDO"){
                    //do nothing
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView1)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Setup Firebase.
    //Add items to arraylists.
    //Set RecylcerView Adapter.
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
                            newList.add(number)
                            binding.button2.text = "£${newList.sum()}"
                        }
                    }
                }
            }
            //If firebase database is not obtained run below function.
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: $error")
                Snackbar.make(binding.root, "Please report on Google Play store $error", Snackbar.LENGTH_LONG).show()
            }
        })
    }
}