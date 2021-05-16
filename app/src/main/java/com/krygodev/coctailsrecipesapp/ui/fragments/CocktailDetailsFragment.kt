package com.krygodev.coctailsrecipesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.adapters.CocktailDetailsAdapter
import com.krygodev.coctailsrecipesapp.ui.activities.StartupActivity
import com.krygodev.coctailsrecipesapp.ui.viewmodels.CocktailDetailsViewModel
import com.krygodev.coctailsrecipesapp.ui.viewmodelsproviders.CocktailDetailsViewModelProviderFactory
import com.krygodev.coctailsrecipesapp.util.Resource
import kotlinx.android.synthetic.main.cocktail_details.*
import kotlinx.android.synthetic.main.fragment_cocktail_details.*

class CocktailDetailsFragment : Fragment() {

    lateinit var viewModel: CocktailDetailsViewModel
    lateinit var cocktailAdapter: CocktailDetailsAdapter
    private val args: CocktailDetailsFragmentArgs by navArgs()

    private val TAG = "CocktailDetailsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cocktail_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelProviderFactory = CocktailDetailsViewModelProviderFactory((activity as StartupActivity).repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(CocktailDetailsViewModel::class.java)

        setupRecyclerView()

        val cocktailID = args.cocktailID

        viewModel.getCocktailById(cocktailID)

        cocktailAdapter.setOnItemClickListener { cocktail ->
            if (cocktailInFavImageView.background == context?.let { ContextCompat.getDrawable(it, R.drawable.ic_favourite) }) {
                cocktailInFavImageView.setBackgroundResource(R.drawable.ic_favourite_full)
                viewModel.insertCocktail(cocktail)
                Toast.makeText(context, "${cocktail.strDrink} saved in favourites", Toast.LENGTH_SHORT).show()
            } else {
                cocktailInFavImageView.setBackgroundResource(R.drawable.ic_favourite)
                viewModel.deleteCocktail(cocktail)
                Toast.makeText(context, "${cocktail.strDrink} deleted from favourites", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.cocktails.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { cocktailResponse ->
                        Log.d(TAG, "Success")
                        cocktailDetailsProgressIndicator.visibility = View.INVISIBLE
                        cocktailAdapter.differ.submitList(cocktailResponse.drinks)
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

    }

    private fun setupRecyclerView() {
        cocktailAdapter = CocktailDetailsAdapter()
        cocktailDetailsRecyclerView.apply {
            adapter = cocktailAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}