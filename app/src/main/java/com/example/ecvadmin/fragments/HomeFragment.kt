package com.example.ecomadmin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ecomadmin.R
import com.example.ecomadmin.activity.AllOrderActivity
import com.example.ecomadmin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.b1.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
        }
        binding.b2.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_productFragment)
        }
        binding.b3.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_sliderFragment)
        }
        binding.b4.setOnClickListener{
            startActivity(Intent(requireContext(),AllOrderActivity::class.java))
        }
        binding.b5.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_bannerFragment)
        }
        return binding.root
    }
}