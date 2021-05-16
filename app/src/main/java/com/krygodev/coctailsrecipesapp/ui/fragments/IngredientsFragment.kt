package com.krygodev.coctailsrecipesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.adapters.IngredientsAdapter
import com.krygodev.coctailsrecipesapp.ui.viewmodelsproviders.IngredientsViewModelProviderFactory
import com.krygodev.coctailsrecipesapp.ui.activities.StartupActivity
import com.krygodev.coctailsrecipesapp.ui.viewmodels.IngredientsViewModel
import com.krygodev.coctailsrecipesapp.util.Resource
import kotlinx.android.synthetic.main.card_view_ingredient.*
import kotlinx.android.synthetic.main.fragment_ingredients.*

class IngredientsFragment : Fragment() {

    lateinit var viewModel: IngredientsViewModel
    lateinit var ingredientsAdapter: IngredientsAdapter

    private val TAG = "IngredientsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ingredients, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelProviderFactory = IngredientsViewModelProviderFactory((activity as StartupActivity).repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(IngredientsViewModel::class.java)
        setupRecyclerView()

        ingredientsAdapter.setOnItemClickListener { ingredient ->
            ingredient.inStock = !ingredient.inStock

            if (ingredient.inStock) viewModel.insertIngredient(ingredient)
            else viewModel.deleteIngredient(ingredient)
        }

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

        viewModel.getIngredientsFromDatabase().observe(viewLifecycleOwner, { ingredients ->
            ingredientsAdapter.differ.submitList(ingredients)
        })

        ingredientsSearchView.apply {
            onActionViewExpanded()
            clearFocus()
        }
    }

    private fun setupRecyclerView() {
        ingredientsAdapter = IngredientsAdapter()
        ingredientsRecyclerView.apply {
            adapter = ingredientsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}