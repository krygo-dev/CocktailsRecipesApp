package com.krygodev.coctailsrecipesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.adapters.IngredientsAdapter
import com.krygodev.coctailsrecipesapp.ui.viewmodels.IngredientsViewModel
import com.krygodev.coctailsrecipesapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_ingredients.*

@AndroidEntryPoint
class IngredientsFragment : Fragment(R.layout.fragment_ingredients) {

    private val viewModel: IngredientsViewModel by viewModels()
    private lateinit var ingredientsAdapter: IngredientsAdapter

    private val TAG = "IngredientsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        ingredientsAdapter.setOnItemClickListener { ingredient ->
            ingredient.inStock = !ingredient.inStock

            if (ingredient.inStock) {
                viewModel.insertIngredient(ingredient)
                ingredientsChipGroup.check(R.id.ingredientsInStockChip)
            }
            else viewModel.deleteIngredient(ingredient)
        }

        populateRecyclerView(ingredientsChipGroup.checkedChipId)

        ingredientsChipGroup.setOnCheckedChangeListener { _, checkedId ->
            populateRecyclerView(checkedId)
        }
    }

    private fun setupRecyclerView() {
        ingredientsAdapter = IngredientsAdapter()
        ingredientsRecyclerView.apply {
            adapter = ingredientsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun populateRecyclerView(id: Int) {
        if (id == R.id.ingredientsNotInStockChip) {
            viewModel.ingredients.observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { ingredientsResponse ->
                            Log.d(TAG, "Ingredients successfully loaded!")
                            ingredientsProgressIndicator.visibility = View.INVISIBLE
                            ingredientsAdapter.differ.submitList(ingredientsResponse.drinks)
                        }
                    }
                    is Resource.Error -> {
                        response.message?.let { errorMessage ->
                            Log.e(TAG, "Ann error occured: $errorMessage")
                        }
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "Loading ingredients...")
                        ingredientsProgressIndicator.visibility = View.VISIBLE
                    }
                }
            })
        } else {
            viewModel.getIngredientsFromDatabase().observe(viewLifecycleOwner, { ingredients ->
                ingredientsAdapter.differ.submitList(ingredients)
            })
        }
    }
}