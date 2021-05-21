package com.krygodev.coctailsrecipesapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.adapters.FavouritesAdapter
import com.krygodev.coctailsrecipesapp.ui.viewmodels.FavouritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourite_coctails.*

@AndroidEntryPoint
class FavouriteCoctailsFragment : Fragment(R.layout.fragment_favourite_coctails) {

    private val viewModel: FavouritesViewModel by viewModels()
    private lateinit var favouritesAdapter: FavouritesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        favouritesAdapter.setOnItemClickListener { cocktail ->
            val bundle = Bundle().apply {
                putInt("cocktailID", cocktail.idDrink.toInt())
            }
            findNavController().navigate(R.id.action_favouriteCoctailsFragment_to_cocktailDetailsFragment, bundle)
        }

        favouritesAdapter.setOnItemLongClickListener { cocktail ->
            viewModel.deleteCocktail(cocktail)
            Snackbar.make(view, "Removed from favourites!", Snackbar.LENGTH_SHORT).show()
        }

        viewModel.getFavouritesCocktails().observe(viewLifecycleOwner, { cocktails ->
            favouritesAdapter.differ.submitList(cocktails)
        })

    }

    private fun setupRecyclerView() {
        favouritesAdapter = FavouritesAdapter()
        favouriteCoctailsRecyclerView.apply {
            adapter = favouritesAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }
}