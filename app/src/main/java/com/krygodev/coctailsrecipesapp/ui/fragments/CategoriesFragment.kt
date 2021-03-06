package com.krygodev.coctailsrecipesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.adapters.CategoryAdapter
import com.krygodev.coctailsrecipesapp.ui.viewmodels.CategoriesViewModel
import com.krygodev.coctailsrecipesapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private val viewModel: CategoriesViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    private val TAG = "CategoriesFragment"

    override fun onResume() {
        super.onResume()
        categoriesSearchView.setQuery("", false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        categoryAdapter.setOnItemClickListener { category ->
            val bundle = Bundle().apply {
                putString("categoryName", category.strCategory)
            }
            findNavController().navigate(R.id.action_categoriesFragment_to_allCocktailsFragment, bundle)
        }

        viewModel.categories.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { categoryResponse ->
                        Log.d(TAG, "Success")
                        categoriesProgressIndicator.visibility = View.INVISIBLE
                        categoryAdapter.differ.submitList(categoryResponse.drinks)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { errorMessage ->
                        Log.e(TAG, "An error occured: $errorMessage")
                    }
                }
                is Resource.Loading -> {
                    Log.d(TAG, "Loading...")
                    categoriesProgressIndicator.visibility = View.VISIBLE
                }
            }
        })

        var job: Job? = null
        categoriesSearchView.apply {
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    job?.cancel()
                    job = MainScope().launch {
                        delay(500L)
                        if (query.isNullOrEmpty()) categoriesSearchView.isIconified = true
                        query?.let {
                            if (query.isNotEmpty()) {
                                val bundle = Bundle().apply {
                                    putString("query", query)
                                }
                                findNavController().navigate(R.id.action_categoriesFragment_to_searchCocktailFragment, bundle)
                            }
                        }
                    }
                    return true
                }

            })
        }
    }

    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter()
        categoriesRecyclerView.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}