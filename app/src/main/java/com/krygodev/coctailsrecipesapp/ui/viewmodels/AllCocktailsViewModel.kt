package com.krygodev.coctailsrecipesapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krygodev.coctailsrecipesapp.data.AllCategories
import com.krygodev.coctailsrecipesapp.data.AllCocktailsFromCategory
import com.krygodev.coctailsrecipesapp.repository.CocktailsRepository
import com.krygodev.coctailsrecipesapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class AllCocktailsViewModel(
    val cocktailsRepository: CocktailsRepository
) : ViewModel() {

    val cocktails: MutableLiveData<Resource<AllCocktailsFromCategory>> = MutableLiveData()

    fun getCocktailsFromCategory(category: String) = viewModelScope.launch {
        cocktails.postValue(Resource.Loading())
        val response = cocktailsRepository.getCocktailsFromCategory(category)
        cocktails.postValue(handleCocktailsFromCategoryResponse(response))
    }

    private fun handleCocktailsFromCategoryResponse(response: Response<AllCocktailsFromCategory>) : Resource<AllCocktailsFromCategory> {
        if (response.isSuccessful) response.body()?.let { result ->
            return Resource.Success(result)
        }
        return Resource.Error(response.message())
    }
}