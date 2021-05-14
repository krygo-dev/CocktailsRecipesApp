package com.krygodev.coctailsrecipesapp.ui.viewmodelsproviders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.krygodev.coctailsrecipesapp.repository.CocktailsRepository
import com.krygodev.coctailsrecipesapp.ui.viewmodels.CocktailDetailsViewModel

class CocktailDetailsViewModelProviderFactory(
    val cocktailsRepository: CocktailsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CocktailDetailsViewModel(cocktailsRepository) as T
    }
}