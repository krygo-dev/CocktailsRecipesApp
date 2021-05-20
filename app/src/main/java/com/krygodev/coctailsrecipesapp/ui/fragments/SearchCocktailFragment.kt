package com.krygodev.coctailsrecipesapp.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.adapters.SearchCocktailAdapter
import com.krygodev.coctailsrecipesapp.ui.activities.StartupActivity
import com.krygodev.coctailsrecipesapp.ui.viewmodels.SearchCocktailViewModel
import com.krygodev.coctailsrecipesapp.ui.viewmodelsproviders.SearchCocktailViewModelProviderFactory
import com.krygodev.coctailsrecipesapp.util.Resource
import kotlinx.android.synthetic.main.fragment_search_cocktail.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchCocktailFragment : Fragment() {

    lateinit var viewModel: SearchCocktailViewModel
    lateinit var searchAdapter: SearchCocktailAdapter
    private val args: SearchCocktailFragmentArgs by navArgs()

    private val TAG = "SearchCocktailFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_cocktail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelProviderFactory = SearchCocktailViewModelProviderFactory((activity as StartupActivity).repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(SearchCocktailViewModel::class.java)
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