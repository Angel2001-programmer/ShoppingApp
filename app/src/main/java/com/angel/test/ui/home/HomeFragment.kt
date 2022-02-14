package com.angel.test.ui.home

import com.angel.test.Communicator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.angel.test.*
import com.angel.test.databinding.FragmentHomeBinding

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

        homeViewModel.getAllMovies()

        communicator = activity as Communicator

        adapter.setOnItemClickListener(object : PhotoAdapter.onitemClickListener{
            override fun onItemClick(position: Int) {

                val image = adapter.movieList.get(position).image
                val name = adapter.movieList.get(position).title
                val desc = adapter.movieList.get(position).description
                val price = adapter.movieList.get(position).price
//                val rates = adapter.movieList.get(position).rating

                communicator.passData(image, name, desc, price)

                Log.d(TAG, "onItemClick: $position")
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}