package com.krygodev.coctailsrecipesapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.krygodev.coctailsrecipesapp.repository.CocktailsRepository

class IngredientsViewModel(
    val cocktailsRepository: CocktailsRepository
) : ViewModel() {
}