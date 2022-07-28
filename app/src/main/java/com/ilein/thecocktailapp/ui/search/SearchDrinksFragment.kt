package com.ilein.thecocktailapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilein.thecocktailapp.databinding.SearchDrinksFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchDrinksFragment : Fragment() {

    private val vm: SearchDrinksViewModel by viewModel()

    private lateinit var binding: SearchDrinksFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchDrinksFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lCardDrinksRecycler.visibility = View.VISIBLE
        //binding.loadingDrinks.visibility = View.INVISIBLE

        val drinksAdapter = DrinkItemAdapter ({
            findNavController().navigate(SearchDrinksFragmentDirections.toDrinkDetailWithParam(it.id))
        },
            { drink, isLike ->
                vm.onLikeClick(drink, isLike)
            }
        )

        vm.state.observe(requireActivity()) {state ->
            run {
                binding.lCardDrinksRecycler.visibility =
                    if (state.loading) View.INVISIBLE else View.VISIBLE
                //binding.loadingDrinks.visibility =
                //    if (state.loading) View.VISIBLE else View.INVISIBLE
                binding.twHint.visibility =
                    if (state.itemsMap.isNotEmpty()) View.GONE else View.VISIBLE

                binding.swipeRefresh.isRefreshing = state.loading

                if (state.isOnline) {
                    drinksAdapter.setItems(state.itemsMap)
                } else {
                    val toast = Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG)
                    toast.show()
                }

            }
        }

        binding.swipeRefresh.setOnRefreshListener { vm.onRefresh() }

        binding.lCardDrinksRecycler.adapter = drinksAdapter
        binding.lCardDrinksRecycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.etDrink.addTextChangedListener {
            val input = it?.toString().orEmpty()
            vm.onTextInput(input)
        }
    }

}