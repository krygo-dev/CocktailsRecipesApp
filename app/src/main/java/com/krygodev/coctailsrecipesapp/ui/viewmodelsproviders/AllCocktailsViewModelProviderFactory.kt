package com.krygodev.coctailsrecipesapp.ui.viewmodelsproviders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.krygodev.coctailsrecipesapp.repository.CocktailsRepository
import com.krygodev.coctailsrecipesapp.ui.viewmodels.AllCocktailsViewModel

class AllCocktailsViewModelProviderFactory(
    val cocktailsRepository: CocktailsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AllCocktailsViewModel(cocktailsRepository) as T
    }
}