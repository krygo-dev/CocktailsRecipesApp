package com.krygodev.coctailsrecipesapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.ui.activities.StartupActivity
import com.krygodev.coctailsrecipesapp.ui.viewmodels.CocktailDetailsViewModel
import com.krygodev.coctailsrecipesapp.ui.viewmodelsproviders.CocktailDetailsViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_ingredients.*

class CocktailDetailsFragment : Fragment() {

    lateinit var viewModel: CocktailDetailsViewModel

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

    }
}