package com.angel.test.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.angel.test.Communicator
import com.angel.test.PhotoAdapter
import com.angel.test.ViewModelFactory
import com.angel.test.api.MainRepository
import com.angel.test.api.RetrofitService
import com.angel.test.databinding.FragmentHomeBinding
import com.angel.test.fragments.MoreDetailsFragment

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val TAG = "HomeFragment"
    private val adapter = PhotoAdapter()
    private lateinit var communicator: Communicator

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val retrofitService = RetrofitService.getInstance()
    val mainRepository = MainRepository(retrofitService)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Setting Adapter to recyclerview.
        binding.recyclerview.adapter = adapter

        homeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(mainRepository)
        ).get(HomeViewModel::class.java)

        homeViewModel.movieList.observe(viewLifecycleOwner, {
            adapter.setMovies(it)
        })

        homeViewModel.errorMessage.observe(viewLifecycleOwner, {
            Log.d(TAG, "onCreate: " + it)
        })

        homeViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.textView.visibility = View.VISIBLE

            } else {
                binding.progressBar.visibility = View.GONE
                binding.textView.visibility = View.GONE
            }
        })
        //call function to retrieve items for recylcerview.
        homeViewModel.getAllMovies()

        //Get context for communicator interface.
        communicator = activity as Communicator

        adapter.setOnItemClickListener(object : PhotoAdapter.onitemClickListener {
            override fun onItemClick(position: Int) {
                //Get position of objects.
                val id = adapter.movieList.get(position).id
                val image = adapter.movieList.get(position).image
                val name = adapter.movieList.get(position).title
                val desc = adapter.movieList.get(position).description
                val price = adapter.movieList.get(position).price

                //send data over to the MoreDetailsFragment.
                communicator.passProductData(id, image, name, desc, price.toString())
                Log.d(TAG, "onItemClick: $position")
            }
        })
//        homeViewModel.saveItemClicks(adapter)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}