package com.angel.test.ui.slideshow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.angel.test.PhotoAdapter
import com.angel.test.adapters.BasketAdapter
import com.angel.test.databinding.FragmentSlideshowBinding
import com.angel.test.models.Product
import com.google.firebase.database.*

class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel
    private lateinit var dataRef: DatabaseReference
    private lateinit var userList: ArrayList<Product>

    private var _binding: FragmentSlideshowBinding? = null
    private val TAG = "SlideshowFragment"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.recyclerView1.layoutManager = LinearLayoutManager(activity)

        slideshowViewModel = ViewModelProvider(
            this).get(SlideshowViewModel::class.java)

        userList = arrayListOf<Product>()
        getAllBasket()

        binding.button2.setOnClickListener{
            Log.d(TAG, "onCreateView: Purchase is successful")
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getAllBasket() {
        dataRef = FirebaseDatabase.getInstance().getReference("Products")
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(productSnapshot in snapshot.children){
                        val product = productSnapshot.getValue(Product::class.java)
                        userList.add(product!!)
                    }
                    binding.recyclerView1.adapter = BasketAdapter(userList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}