package com.angel.test.ui.slideshow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.angel.test.databinding.FragmentSlideshowBinding
import com.angel.test.image
import com.angel.test.name
import com.angel.test.price

class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel
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
        slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        name = arguments?.get("title") as String?
        image = arguments?.get("uri") as String?
       val price: String = arguments?.get("price") as String

        Log.d(TAG, "onCreateView: $name, $image, $price")

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}