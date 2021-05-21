package com.krygodev.coctailsrecipesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.adapters.RandomCocktailAdapter
import com.krygodev.coctailsrecipesapp.ui.viewmodels.RandomViewModel
import com.krygodev.coctailsrecipesapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_random_coctail.*

@AndroidEntryPoint
class RandomCoctailFragment : Fragment(R.layout.fragment_random_coctail) {

    private val viewModel: RandomViewModel by viewModels()
    private lateinit var randomCocktailAdapter: RandomCocktailAdapter

    private val TAG = "RandomCocktailFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        randomCocktailAdapter.setOnItemClickListener { cocktail ->
            if (cocktail.inStock) {
                viewModel.insertCocktail(cocktail).invokeOnCompletion {
                    randomCocktailAdapter.ingInStock = viewModel.ingredientsInStock
                    randomCocktailAdapter.cocktailsInFav = viewModel.cocktailsInFav
                }
            } else {
                viewModel.deleteCocktail(cocktail).invokeOnCompletion {
                    randomCocktailAdapter.ingInStock = viewModel.ingredientsInStock
                    randomCocktailAdapter.cocktailsInFav = viewModel.cocktailsInFav
                }
            }
        }

        viewModel.cocktails.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { cocktailResponse ->
                        Log.d(TAG, "Success")
                        randomCocktailProgressIndicator.visibility = View.INVISIBLE
                        randomCocktailAdapter.differ.submitList(cocktailResponse.drinks)
                        randomCocktailAdapter.ingInStock = viewModel.ingredientsInStock
                        randomCocktailAdapter.cocktailsInFav = viewModel.cocktailsInFav
                    }
                }
                is Resource.Error -> {
                    response.message?.let { errorMessage ->
                        Log.e(TAG, "An error occured: $errorMessage")
                    }
                }
                is Resource.Loading -> {
                    Log.d(TAG, "Loading...")
                    randomCocktailProgressIndicator.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupRecyclerView() {
        randomCocktailAdapter = RandomCocktailAdapter()
        randomCocktailRecyclerView.apply {
            adapter = randomCocktailAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}