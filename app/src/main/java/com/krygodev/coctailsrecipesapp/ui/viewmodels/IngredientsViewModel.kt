package com.krygodev.coctailsrecipesapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krygodev.coctailsrecipesapp.data.AllIngredients
import com.krygodev.coctailsrecipesapp.data.Ingredient
import com.krygodev.coctailsrecipesapp.repository.CocktailsRepository
import com.krygodev.coctailsrecipesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class IngredientsViewModel @Inject constructor(
    val cocktailsRepository: CocktailsRepository
) : ViewModel() {

    val ingredients: MutableLiveData<Resource<AllIngredients>> = MutableLiveData()
    var ingredientsInStock: List<Ingredient> = listOf()

    init {
        getAllIngredients()
    }

    fun insertIngredient(ingredient: Ingredient) = viewModelScope.launch {
        cocktailsRepository.insertIngredient(ingredient)
        ingredientsInStock = cocktailsRepository.getIngredientsList()
    }

    fun deleteIngredient(ingredient: Ingredient) = viewModelScope.launch {
        cocktailsRepository.deleteIngredient(ingredient)
        ingredientsInStock = cocktailsRepository.getIngredientsList()
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
            return Resource.Success(result)
        }
        return Resource.Error(response.message())
    }
}
