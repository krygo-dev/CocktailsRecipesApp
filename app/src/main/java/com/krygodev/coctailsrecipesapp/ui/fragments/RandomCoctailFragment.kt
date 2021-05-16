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
import com.krygodev.coctailsrecipesapp.adapters.RandomCocktailAdapter
import com.krygodev.coctailsrecipesapp.ui.viewmodelsproviders.RandomViewModelProviderFactory
import com.krygodev.coctailsrecipesapp.ui.activities.StartupActivity
import com.krygodev.coctailsrecipesapp.ui.viewmodels.RandomViewModel
import com.krygodev.coctailsrecipesapp.util.Resource
import kotlinx.android.synthetic.main.fragment_random_coctail.*

class RandomCoctailFragment : Fragment() {

    lateinit var viewModel: RandomViewModel
    lateinit var randomCocktailAdapter: RandomCocktailAdapter

    private val TAG = "RandomCocktailFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_random_coctail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelProviderFactory = RandomViewModelProviderFactory((activity as StartupActivity).repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(RandomViewModel::class.java)

        setupRecyclerView()

        viewModel.cocktails.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { cocktailResponse ->
                        Log.d(TAG, "Success")
                        randomCocktailAdapter.differ.submitList(cocktailResponse.drinks)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { errorMessage ->
                        Log.e(TAG, "An error occured: $errorMessage")
                    }
                }
                is Resource.Loading -> Log.d(TAG, "Loading...")
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