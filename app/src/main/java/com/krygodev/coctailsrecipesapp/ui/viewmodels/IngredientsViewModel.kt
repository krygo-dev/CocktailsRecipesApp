package com.krygodev.coctailsrecipesapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krygodev.coctailsrecipesapp.data.AllIngredients
import com.krygodev.coctailsrecipesapp.data.Ingredient
import com.krygodev.coctailsrecipesapp.repository.CocktailsRepository
import com.krygodev.coctailsrecipesapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class IngredientsViewModel(
    val cocktailsRepository: CocktailsRepository
) : ViewModel() {

    val ingredients: MutableLiveData<Resource<AllIngredients>> = MutableLiveData()
    lateinit var ingredientsInStock: List<Ingredient>

    init {
//        viewModelScope.launch {
//            ingredientsInStock = cocktailsRepository.getIngredientsList()
//        }
        getAllIngredients()
    }

    fun insertIngredient(ingredient: Ingredient) = viewModelScope.launch {
        cocktailsRepository.insertIngredient(ingredient)
    }

    fun deleteIngredient(ingredient: Ingredient) = viewModelScope.launch {
        cocktailsRepository.deleteIngredient(ingredient)
    }

    fun getIngredientsFromDatabase() = cocktailsRepository.getIngredientsFromDatabase()

    private fun getAllIngredients() = viewModelScope.launch {
        ingredientsInStock = cocktailsRepository.getIngredientsList()
        ingredients.postValue(Resource.Loading())
        val response = cocktailsRepository.getAllIngredients()
        ingredients.postValue(handleAllIngredientsResponse(response))
    }

    private fun handleAllIngredientsResponse(response: Response<AllIngredients>): Resource<AllIngredients> {
        if (response.isSuccessful) response.body()?.let { result ->
            result.drinks.forEach { ingredient ->
                if (ingredientsInStock.contains(ingredient)) {
                    result.drinks.minus(ingredient)
                }
            }
            return Resource.Success(result)
        }
        return Resource.Error(response.message())
    }
}
