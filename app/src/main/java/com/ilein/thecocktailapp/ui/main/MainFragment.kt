package com.ilein.thecocktailapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ilein.thecocktailapp.R
import com.ilein.thecocktailapp.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGoToSearch.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchDrinksFragment)
        }
        binding.btnGoToLikedDrinks.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_likedDrinksFragment)
        }
        binding.btnGoToMap.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_mapFragment)
        }
    }

}