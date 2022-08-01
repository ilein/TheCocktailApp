package com.ilein.thecocktailapp.presentation.ui.liked

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilein.thecocktailapp.databinding.LikedDrinksFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LikedDrinksFragment : Fragment() {

    private lateinit var binding: LikedDrinksFragmentBinding

    private val vm: LikedDrinksViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LikedDrinksFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drinksAdapter = LikedDrinkItemAdapter ({
            findNavController().navigate(LikedDrinksFragmentDirections.toDrinkLikeDetailWithParam(it.id))
        },
            {
                vm.onDelClick(it)
            }
        )

        vm.state.observe(requireActivity()) {state ->
            run {
                binding.recyclerLiked.visibility = if (state.loading) View.INVISIBLE else View.VISIBLE
                binding.loadingLikedDrinks.visibility =
                    if (state.loading) View.VISIBLE else View.INVISIBLE
                drinksAdapter.setItems(state.items)
            }
        }

        binding.recyclerLiked.adapter = drinksAdapter
        binding.recyclerLiked.layoutManager = LinearLayoutManager(requireActivity())

    }

}