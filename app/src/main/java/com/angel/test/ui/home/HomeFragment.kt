package com.angel.test.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.angel.test.MainRepository
import com.angel.test.MovieAdapter
import com.angel.test.RetrofitService
import com.angel.test.ViewModelFactory
import com.angel.test.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val TAG = "HomeFragment"
    private val adapter = MovieAdapter()

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
//        binding.recyclerview.adapter = adapter

//        homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
//
//        homeViewModel = ViewModelProvider(
//            this,
//            ViewModelFactory(mainRepository)
//        ).get(HomeViewModel::class.java)
//
//        homeViewModel.movieList.observe(viewLifecycleOwner, {
//            adapter.setMovies(it)
//        })
//
//        homeViewModel.errorMessage.observe(viewLifecycleOwner, {
//            Log.d(TAG, "onCreate: " + it)
//        })
//
//        homeViewModel.loading.observe(viewLifecycleOwner, Observer {
//            if (it) {
//                binding.progressBar.visibility = View.VISIBLE
//            } else {
//                binding.progressBar.visibility = View.GONE
//            }
//        })

//        homeViewModel.getAllMovies()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}