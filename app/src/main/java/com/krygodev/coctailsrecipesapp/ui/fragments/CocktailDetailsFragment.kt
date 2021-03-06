package com.krygodev.coctailsrecipesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.adapters.CocktailDetailsAdapter
import com.krygodev.coctailsrecipesapp.ui.viewmodels.CocktailDetailsViewModel
import com.krygodev.coctailsrecipesapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cocktail_details.*

@AndroidEntryPoint
class CocktailDetailsFragment : Fragment(R.layout.fragment_cocktail_details) {

    private val viewModel: CocktailDetailsViewModel by viewModels()
    private val args: CocktailDetailsFragmentArgs by navArgs()
    private lateinit var cocktailAdapter: CocktailDetailsAdapter

    private val TAG = "CocktailDetailsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        val cocktailID = args.cocktailID

        viewModel.getCocktailById(cocktailID)

        cocktailAdapter.setOnItemClickListener { cocktail ->
            if (cocktail.inStock) {
                Snackbar.make(view, "Added to favourites!", Snackbar.LENGTH_SHORT).show()
                viewModel.insertCocktail(cocktail).invokeOnCompletion {
                    cocktailAdapter.ingInStock = viewModel.ingredientsInStock
                    cocktailAdapter.cocktailsInFav = viewModel.cocktailsInFav
                }
            } else {
                Snackbar.make(view, "Removed from favourites!", Snackbar.LENGTH_SHORT).show()
                viewModel.deleteCocktail(cocktail).invokeOnCompletion {
                    cocktailAdapter.ingInStock = viewModel.ingredientsInStock
                    cocktailAdapter.cocktailsInFav = viewModel.cocktailsInFav
                }
            }
        }

        viewModel.cocktails.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { cocktailResponse ->
                        Log.d(TAG, "Success")
                        cocktailDetailsProgressIndicator.visibility = View.INVISIBLE
                        cocktailAdapter.differ.submitList(cocktailResponse.drinks)
                        cocktailAdapter.ingInStock = viewModel.ingredientsInStock
                        cocktailAdapter.cocktailsInFav = viewModel.cocktailsInFav
                    }
                }
                is Resource.Error -> {
                    response.message?.let { errorMessage ->
                        Log.e(TAG, "An error occured: $errorMessage")
                    }
                }
                is Resource.Loading -> {
                    Log.d(TAG, "Loading...")
                    cocktailDetailsProgressIndicator.visibility = View.VISIBLE
                }
            }
        })

        cocktailDetailsBackImageView.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun setupRecyclerView() {
        cocktailAdapter = CocktailDetailsAdapter()
        cocktailDetailsRecyclerView.apply {
            adapter = cocktailAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}