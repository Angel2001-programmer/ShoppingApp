
package com.angel.test

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.angel.test.databinding.FragmentMoreDetailsBinding
import com.angel.test.ui.slideshow.SlideshowFragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "MoreDetailsFragment"

//View databinding
private lateinit var binding: FragmentMoreDetailsBinding
private lateinit var communicator: Communicator

//General Variables
var image: String? = null
var name: String? = null
var desc: String? = null
var price: Double? = 0.00
//var rating: Double? = 0.00

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
        image = arguments?.getString("uri")
        name = arguments?.getString("title")
        desc = arguments?.getString("description")
        price = arguments?.getDouble("price")
//        rating = arguments?.getDouble("rating")

        // Setting text and image.
        Glide.with(this)
            .load(image)
            .override(800,800)
            .into(binding.img)

        binding.tvTitle.text = name
        binding.tvDesc.text = desc
        binding.tvPrice.text = "£" + price.toString()
//        binding.ratingBar.rating = rating.toFloat()
        binding.button.text = "Add to Basket " + "£" + price.toString()
        communicator = activity as Communicator
//        Log.d(TAG, "onCreateView: $rating")
        binding.button.setOnClickListener {
            Log.d(TAG, "onCreateView: clicked")
            image?.let { it1 -> name?.let { it2 -> price?.let { it3 ->
                communicator.passBasketInfo(it1, it2,
                    binding.tvPrice.text.toString()
                )
            } } }
            Log.d(TAG, "onCreateView: " + binding.tvTitle.text.toString() + binding.tvTitle.text.toString() + binding.tvPrice.text.toString())
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