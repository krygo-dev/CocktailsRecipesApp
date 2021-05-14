package com.krygodev.coctailsrecipesapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.krygodev.coctailsrecipesapp.repository.CocktailsRepository

class CocktailDetailsViewModel(
    val cocktailsRepository: CocktailsRepository
) : ViewModel() {
}