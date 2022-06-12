package com.ilein.thecocktailapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.ilein.thecocktailapp.databinding.FragmentDrinkLikeItemDetailBinding
import com.ilein.thecocktailapp.ui.state.DrinkItemLikeState
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 * Use the [DrinkLikeItemDetailFragment factory method to
 * create an instance of this fragment.
 */
class DrinkLikeItemDetailFragment : Fragment() {

    private lateinit var binding: FragmentDrinkLikeItemDetailBinding
    private val args: DrinkLikeItemDetailFragmentArgs by navArgs()

    private val vm: DrinkLikeItemDetailViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrinkLikeItemDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drinkId = args.drinkId
        vm.state.observe(requireActivity()) {state -> handleState(state) }
        vm.init(drinkId)

        binding.ibSendLike.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT, "Hi! Try this:" +
                            "\n${binding.drinkTitleLike.text}" +
                            "\n${binding.ingredientsLike.text}" +
                            "\n${binding.instructionsLike.text}")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun handleState(state: DrinkItemLikeState) {
        binding.lCardItemLike.visibility = if (state.loading) View.INVISIBLE else View.VISIBLE
        binding.loadingLike.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE

        state.drink?.let {
            binding.posterLike.load("${state.drink.strDrinkThumb}") {
                transformations(RoundedCornersTransformation(16f))
            }
            binding.drinkTitleLike.text = state.drink.strDrink
            binding.instructionsLike.text = state.drink.strInstructions
            binding.ingredientsLike.text = state.drink.getIngredientsText()
        }
    }

}