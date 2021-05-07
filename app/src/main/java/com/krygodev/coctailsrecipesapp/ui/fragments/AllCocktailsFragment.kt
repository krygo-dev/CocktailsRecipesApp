package com.krygodev.coctailsrecipesapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.ui.viewmodelsproviders.AllCocktailsViewModelProviderFactory
import com.krygodev.coctailsrecipesapp.ui.activities.StartupActivity
import com.krygodev.coctailsrecipesapp.ui.viewmodels.AllCocktailsViewModel
import kotlinx.android.synthetic.main.fragment_all_coctails.*

class AllCocktailsFragment : Fragment() {

    lateinit var viewModel: AllCocktailsViewModel

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

        allCoctailsSearchView.apply {
            onActionViewExpanded()
            clearFocus()
        }
    }
}