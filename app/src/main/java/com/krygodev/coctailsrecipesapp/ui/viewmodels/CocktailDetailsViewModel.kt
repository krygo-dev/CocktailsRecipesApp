package com.krygodev.coctailsrecipesapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krygodev.coctailsrecipesapp.data.AllCocktails
import com.krygodev.coctailsrecipesapp.data.Cocktail
import com.krygodev.coctailsrecipesapp.repository.CocktailsRepository
import com.krygodev.coctailsrecipesapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class CocktailDetailsViewModel(
    val cocktailsRepository: CocktailsRepository
) : ViewModel() {

    val cocktails: MutableLiveData<Resource<AllCocktails>> = MutableLiveData()

    fun getCocktailById(id: Int) = viewModelScope.launch {
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

    fun insertCocktail(cocktail: Cocktail) = viewModelScope.launch {
        cocktailsRepository.insertCocktail(cocktail)
    }

    fun deleteCocktail(cocktail: Cocktail) = viewModelScope.launch {
        cocktailsRepository.deleteCocktail(cocktail)
    }

    fun getCocktailsFromDatabse() = cocktailsRepository.getCocktailsFromDatabase()
}