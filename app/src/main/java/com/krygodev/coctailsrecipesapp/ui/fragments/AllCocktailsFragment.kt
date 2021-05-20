package com.krygodev.coctailsrecipesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.adapters.CategoryCocktailsAdapter
import com.krygodev.coctailsrecipesapp.ui.activities.StartupActivity
import com.krygodev.coctailsrecipesapp.ui.viewmodels.AllCocktailsViewModel
import com.krygodev.coctailsrecipesapp.ui.viewmodelsproviders.AllCocktailsViewModelProviderFactory
import com.krygodev.coctailsrecipesapp.util.Resource
import kotlinx.android.synthetic.main.fragment_all_coctails.*

class AllCocktailsFragment : Fragment() {

    lateinit var viewModel: AllCocktailsViewModel
    lateinit var cocktailsAdapter: CategoryCocktailsAdapter
    private val args: AllCocktailsFragmentArgs by navArgs()

    private val TAG = "AllCocktailsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_coctails, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelProviderFactory = AllCocktailsViewModelProviderFactory((activity as StartupActivity).repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(AllCocktailsViewModel::class.java)

        setupRecyclerView()

        val categoryName = args.categoryName

        viewModel.getCocktailsFromCategory(categoryName)

        cocktailsAdapter.setOnItemClickListener { cocktailFromCategory ->
            val bundle = Bundle().apply {
                putInt("cocktailID", cocktailFromCategory.idDrink.toInt())
            }
            findNavController().navigate(R.id.action_allCocktailsFragment_to_cocktailDetailsFragment, bundle)
        }

        viewModel.cocktails.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { categoryResponse ->
                        Log.d(TAG, "Success")
                        allCocktailsProgressIndicator.visibility = View.INVISIBLE
                        cocktailsAdapter.differ.submitList(categoryResponse.drinks)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { errorMessage ->
                        Log.e(TAG, "An error occured: $errorMessage")
                    }
                }
                is Resource.Loading -> {
                    Log.d(TAG, "Loading...")
                    allCocktailsProgressIndicator.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupRecyclerView() {
        cocktailsAdapter = CategoryCocktailsAdapter()
        allCoctailsRecyclerView.apply {
            adapter = cocktailsAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }
}