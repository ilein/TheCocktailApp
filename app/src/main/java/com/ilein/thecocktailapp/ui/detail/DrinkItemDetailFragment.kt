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
import com.ilein.thecocktailapp.R
import com.ilein.thecocktailapp.databinding.FragmentDrinkItemDetailBinding
import com.ilein.thecocktailapp.ui.state.DrinkItemState
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [DrinkItemDetailFragment] factory method to
 * create an instance of this fragment.
 */
class DrinkItemDetailFragment : Fragment() {

    private val vm: DrinkItemDetailViewModel by viewModel()

    private lateinit var binding: FragmentDrinkItemDetailBinding
    private val args: DrinkItemDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrinkItemDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drinkId = args.drinkId
        vm.state.observe(requireActivity()) {state -> handleState(state) }
        vm.init(drinkId, false)

        binding.ibSend.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT, "Hi! Try this:" +
                        "\n${binding.drinkTitle.text}" +
                        "\n${binding.ingredients.text}" +
                        "\n${binding.instructions.text}")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        binding.twLabelIngredients.setOnClickListener {
            binding.ingredients.visibility = if (binding.ingredients.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            binding.twLabelIngredients.text = if (binding.ingredients.visibility == View.VISIBLE) getString(R.string.ingredient_label_down) else
                getString(R.string.ingredient_label_up)
        }

        binding.twLabelInstructions.setOnClickListener {
            binding.instructions.visibility = if (binding.instructions.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            binding.twLabelInstructions.text = if (binding.instructions.visibility == View.VISIBLE) getString(R.string.instructions_label_down) else
                getString(R.string.instructions_label_up)
        }
    }

    private fun handleState(state: DrinkItemState) {
        binding.lCardItem.visibility = if (state.loading) View.INVISIBLE else View.VISIBLE
        binding.loading.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE

        state.item?.let {
            binding.poster.load("${it.drink.strDrinkThumb}") {
                transformations(RoundedCornersTransformation(16f))
            }
            binding.drinkTitle.text = it.drink.strDrink
            binding.instructions.text = it.drink.strInstructions
            binding.ingredients.text = it.drink.getIngredientsText()
        }
    }
}