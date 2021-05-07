package com.krygodev.coctailsrecipesapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.krygodev.coctailsrecipesapp.repository.CocktailsRepository

class CategoriesViewModel(
    val cocktailsRepository: CocktailsRepository
) : ViewModel() {
}