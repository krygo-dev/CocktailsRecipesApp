package com.krygodev.coctailsrecipesapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.krygodev.coctailsrecipesapp.repository.CocktailsRepository
import com.krygodev.coctailsrecipesapp.ui.viewmodels.IngredientsViewModel

class IngredientsViewModelProviderFactory(
    val cocktailsRepository: CocktailsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return IngredientsViewModel(cocktailsRepository) as T
    }
}