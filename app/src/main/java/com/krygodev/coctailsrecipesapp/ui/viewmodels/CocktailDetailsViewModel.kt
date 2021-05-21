package com.krygodev.coctailsrecipesapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krygodev.coctailsrecipesapp.data.AllCocktails
import com.krygodev.coctailsrecipesapp.data.Cocktail
import com.krygodev.coctailsrecipesapp.data.Ingredient
import com.krygodev.coctailsrecipesapp.repository.CocktailsRepository
import com.krygodev.coctailsrecipesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CocktailDetailsViewModel @Inject constructor(
    val cocktailsRepository: CocktailsRepository
) : ViewModel() {

    val cocktails: MutableLiveData<Resource<AllCocktails>> = MutableLiveData()
    var ingredientsInStock: List<Ingredient> = listOf()
    var cocktailsInFav: List<Cocktail> = listOf()

    fun insertCocktail(cocktail: Cocktail) = viewModelScope.launch {
        cocktailsRepository.insertCocktail(cocktail)
        ingredientsInStock = cocktailsRepository.getIngredientsList()
        cocktailsInFav = cocktailsRepository.getCocktailsList()
    }

    fun deleteCocktail(cocktail: Cocktail) = viewModelScope.launch {
        cocktailsRepository.deleteCocktail(cocktail)
        ingredientsInStock = cocktailsRepository.getIngredientsList()
        cocktailsInFav = cocktailsRepository.getCocktailsList()
    }

    fun getCocktailById(id: Int) = viewModelScope.launch {
        ingredientsInStock = cocktailsRepository.getIngredientsList()
        cocktailsInFav = cocktailsRepository.getCocktailsList()
        cocktails.postValue(Resource.Loading())
        val response = cocktailsRepository.getCocktailById(id)
        cocktails.postValue(handleCocktailByIdResponse(response))
    }

    private fun handleCocktailByIdResponse(response: Response<AllCocktails>): Resource<AllCocktails> {
        if (response.isSuccessful) response.body()?.let { result ->
            return Resource.Success(result)
        }
        return Resource.Error(response.message())
    }
}