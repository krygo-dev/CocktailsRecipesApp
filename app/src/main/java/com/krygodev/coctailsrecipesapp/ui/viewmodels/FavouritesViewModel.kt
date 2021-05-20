package com.krygodev.coctailsrecipesapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krygodev.coctailsrecipesapp.data.Cocktail
import com.krygodev.coctailsrecipesapp.repository.CocktailsRepository
import kotlinx.coroutines.launch

class FavouritesViewModel(
    val cocktailsRepository: CocktailsRepository
) : ViewModel() {
    fun getFavouritesCocktails() = cocktailsRepository.getCocktailsFromDatabase()

    fun deleteCocktail(cocktail: Cocktail) = viewModelScope.launch {
        cocktailsRepository.deleteCocktail(cocktail)
    }
}