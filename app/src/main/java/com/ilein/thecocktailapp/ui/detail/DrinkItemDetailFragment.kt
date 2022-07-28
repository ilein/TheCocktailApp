package com.ilein.thecocktailapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.button.MaterialButton
import com.ilein.thecocktailapp.R
import com.ilein.thecocktailapp.databinding.FragmentDrinkItemDetailBinding
import com.ilein.thecocktailapp.ui.state.DrinkItemState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drinkId = args.drinkId
        vm.state.observe(requireActivity()) {state -> handleState(state) }

        // TODO доделать отображение лайка
        GlobalScope.launch (Dispatchers.IO) {
            vm.init(drinkId, false)
            binding.swipeRefresh.setOnRefreshListener {
                GlobalScope.launch (Dispatchers.IO) {
                    vm.onRefresh(drinkId, false)
                }}
        }
        binding.ibSend.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT, "Hi! Try this:" +
                        "\n${binding.poster.contentDescription}" +
                        "\n${binding.drinkTitle.text}" +
                        "\n${binding.ingredients.text}" +
                        "\n${binding.instructions.text}")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        binding.btnExpandIngredients.setOnClickListener {
            if (binding.ingredients.visibility == View.VISIBLE) {
                binding.ingredients.visibility = View.GONE
                (binding.btnExpandIngredients as MaterialButton).setIconResource(R.drawable.ic_expand_less)
            } else {
                binding.ingredients.visibility = View.VISIBLE
                (binding.btnExpandIngredients as MaterialButton).setIconResource(R.drawable.ic_expand_more)
            }
        }

        binding.btnExpandInstructions.setOnClickListener {
            if (binding.instructions.visibility == View.VISIBLE) {
                binding.instructions.visibility = View.GONE
                (binding.btnExpandInstructions as MaterialButton).setIconResource(R.drawable.ic_expand_less)
            } else {
                binding.instructions.visibility = View.VISIBLE
                (binding.btnExpandInstructions as MaterialButton).setIconResource(R.drawable.ic_expand_more)
            }
        }
    }

    private fun handleState(state: DrinkItemState) {
        binding.lCardItem.visibility = if (state.loading || !state.isOnline) View.INVISIBLE else View.VISIBLE
        binding.swipeRefresh.isRefreshing = state.loading

        if (state.isOnline) {
            state.item?.let {
                binding.poster.contentDescription = it.drink.image
                binding.poster.load("${it.drink.image}") {
                    transformations(RoundedCornersTransformation(16f))
                }
                binding.drinkTitle.text = it.drink.name
                binding.instructions.text = it.drink.instructions
                binding.ingredients.text = it.drink.ingredients
            }
        } else {
            val toast = Toast.makeText(context, "No Internet Connection!!!", Toast.LENGTH_LONG)
            toast.show()
        }
    }
}