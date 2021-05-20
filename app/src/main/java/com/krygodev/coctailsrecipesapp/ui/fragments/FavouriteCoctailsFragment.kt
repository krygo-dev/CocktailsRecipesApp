package com.krygodev.coctailsrecipesapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.adapters.FavouritesAdapter
import com.krygodev.coctailsrecipesapp.ui.viewmodelsproviders.FavouritesViewModelProviderFactory
import com.krygodev.coctailsrecipesapp.ui.activities.StartupActivity
import com.krygodev.coctailsrecipesapp.ui.viewmodels.FavouritesViewModel
import kotlinx.android.synthetic.main.fragment_favourite_coctails.*

class FavouriteCoctailsFragment : Fragment() {

    lateinit var viewModel: FavouritesViewModel
    lateinit var favouritesAdapter: FavouritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite_coctails, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelProviderFactory = FavouritesViewModelProviderFactory((activity as StartupActivity).repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(FavouritesViewModel::class.java)

        setupRecyclerView()

        favouritesAdapter.setOnItemClickListener { cocktail ->
            val bundle = Bundle().apply {
                putInt("cocktailID", cocktail.idDrink.toInt())
            }
            findNavController().navigate(R.id.action_favouriteCoctailsFragment_to_cocktailDetailsFragment, bundle)
        }

        favouritesAdapter.setOnItemLongClickListener { cocktail ->
            viewModel.deleteCocktail(cocktail)
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