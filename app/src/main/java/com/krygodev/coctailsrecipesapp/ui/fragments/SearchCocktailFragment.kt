package com.krygodev.coctailsrecipesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.adapters.SearchCocktailAdapter
import com.krygodev.coctailsrecipesapp.ui.viewmodels.SearchCocktailViewModel
import com.krygodev.coctailsrecipesapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_cocktail.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchCocktailFragment : Fragment(R.layout.fragment_search_cocktail) {

    private val viewModel: SearchCocktailViewModel by viewModels()
    private val args: SearchCocktailFragmentArgs by navArgs()
    private lateinit var searchAdapter: SearchCocktailAdapter

    private val TAG = "SearchCocktailFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        val query = args.query

        viewModel.getCocktailByName(query)

        searchAdapter.setOnItemClickListener { cocktail ->
            val bundle = Bundle().apply {
                putInt("cocktailID", cocktail.idDrink.toInt())
            }
            findNavController().navigate(R.id.action_searchCocktailFragment_to_cocktailDetailsFragment, bundle)
        }

        viewModel.cocktails.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { cocktailResponse ->
                        Log.d(TAG, "Success")
                        searchCocktailProgressIndicator.visibility = View.INVISIBLE
                        searchAdapter.differ.submitList(cocktailResponse.drinks)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { errorMessage ->
                        Log.e(TAG, "An error occured: $errorMessage")
                    }
                }
                is Resource.Loading -> {
                    Log.d(TAG, "Loading...")
                    searchCocktailProgressIndicator.visibility = View.VISIBLE
                }
            }
        })

        var job: Job? = null
        searchCocktailSearchView.apply {
            onActionViewExpanded()
            clearFocus()
            setQuery(query, false)
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    job?.cancel()
                    job = MainScope().launch {
                        delay(500L)
                        query?.let {
                            viewModel.getCocktailByName(query)
                        }
                    }
                    return true
                }

            })
        }
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchCocktailAdapter()
        searchCocktailRecyclerView.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }
}