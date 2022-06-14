package com.ilein.thecocktailapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.button.MaterialButton
import com.ilein.thecocktailapp.R
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

        binding.swipeRefresh.setOnRefreshListener { vm.onRefresh(drinkId) }

        binding.ibSendLike.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT, "Hi! Try this:" +
                            "\n${binding.posterLike.contentDescription}" +
                            "\n${binding.drinkTitleLike.text}" +
                            "\n${binding.ingredientsLike.text}" +
                            "\n${binding.instructionsLike.text}")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        binding.btnExpandIngredientsLike.setOnClickListener {
            if (binding.ingredientsLike.visibility == View.VISIBLE) {
                binding.ingredientsLike.visibility = View.GONE
                (binding.btnExpandIngredientsLike as MaterialButton).setIconResource(R.drawable.ic_expand_less)
            } else {
                binding.ingredientsLike.visibility = View.VISIBLE
                (binding.btnExpandIngredientsLike as MaterialButton).setIconResource(R.drawable.ic_expand_more)
            }
        }

        binding.btnExpandInstructionsLike.setOnClickListener {
            if (binding.instructionsLike.visibility == View.VISIBLE) {
                binding.instructionsLike.visibility = View.GONE
                (binding.btnExpandInstructionsLike as MaterialButton).setIconResource(R.drawable.ic_expand_less)
            } else {
                binding.instructionsLike.visibility = View.VISIBLE
                (binding.btnExpandInstructionsLike as MaterialButton).setIconResource(R.drawable.ic_expand_more)
            }
        }

    }

    private fun handleState(state: DrinkItemLikeState) {
        binding.lCardItemLike.visibility = if (state.loading || !state.isOnline) View.INVISIBLE else View.VISIBLE
        binding.swipeRefresh.isRefreshing = state.loading

        if (state.isOnline) {
            state.drink?.let {
                binding.posterLike.contentDescription = state.drink.strDrinkThumb
                binding.posterLike.load("${state.drink.strDrinkThumb}") {
                    transformations(RoundedCornersTransformation(16f))
                }
                binding.drinkTitleLike.text = state.drink.strDrink
                binding.instructionsLike.text = state.drink.strInstructions
                binding.ingredientsLike.text = state.drink.getIngredientsText()
            }
        } else {
            val toast = Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG)
            toast.show()
        }
        state.drinkLike?.let {
            binding.tiDrinkLikeNote.setText(state.drinkLike.note)
        }
        binding.tiDrinkLikeNote.text?.let { binding.tiDrinkLikeNote.setSelection(it.length) }
        binding.tiDrinkLikeNote.doOnTextChanged { text, _, _, _ -> vm.onNoteChange(text.toString()) }
        binding.ibSave.setOnClickListener {
            vm.onSaveClick()
            val toast = Toast.makeText(context, "Saved!", Toast.LENGTH_LONG)
            toast.show()
        }
    }

}