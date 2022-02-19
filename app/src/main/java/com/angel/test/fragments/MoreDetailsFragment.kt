package com.angel.test.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.angel.test.Communicator
import com.angel.test.databinding.FragmentMoreDetailsBinding
import com.angel.test.models.Product
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "MoreDetailsFragment"

//View databinding
//Communitor class
//Database
private lateinit var binding: FragmentMoreDetailsBinding
private lateinit var communicator: Communicator
private lateinit var database: DatabaseReference

//General Variables
var productId: Int? = 0
var image: String? = null
var name: String? = null
var desc: String? = null
var price: String? = null

/**
 * A simple [Fragment] subclass.
 * Use the [MoreDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoreDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Retrieve arguements.
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // getting the data and storing it in the new fragment.
        productId = arguments?.getInt("id")
        image = arguments?.getString("uri")
        name = arguments?.getString("title")
        desc = arguments?.getString("description")
        price = arguments?.getString("price")

        // Setting text and image.
        Glide.with(this)
            .load(image)
            .override(800, 800)
            .into(binding.img)

        binding.tvTitle.text = name
        binding.tvDesc.text = desc
        binding.tvPrice.text = "£$price"
        binding.button.text = "Add to Basket " + binding.tvPrice.text
        communicator = activity as Communicator

        //If add to cart button is pressed Add to firebase database.
        binding.button.setOnClickListener {
            Log.d(TAG, "onCreateView: clicked")
            database = FirebaseDatabase.getInstance().getReference("Products")
            val Products = Product(id, image, name, binding.tvPrice.text.toString())
            database.child(productId.toString()).setValue(Products).addOnSuccessListener {
                Log.d(TAG, "onCreateView: Successfully added to cart")
            }.addOnFailureListener {
                Log.d(TAG, "onCreateView: Failed to add to cart")
            }
            //notify user that product has been added to the shopping basket.
            Snackbar.make(root, "Added to Cart \n$name", Snackbar.LENGTH_LONG).show()
        }
        // Inflate the layout for this fragment
        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MoreDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MoreDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}