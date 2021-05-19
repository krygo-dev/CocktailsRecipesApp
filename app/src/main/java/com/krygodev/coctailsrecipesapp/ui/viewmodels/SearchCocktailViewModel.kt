package com.krygodev.coctailsrecipesapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krygodev.coctailsrecipesapp.data.AllCocktails
import com.krygodev.coctailsrecipesapp.repository.CocktailsRepository
import com.krygodev.coctailsrecipesapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchCocktailViewModel(
    val cocktailsRepository: CocktailsRepository
) : ViewModel() {

    val cocktails: MutableLiveData<Resource<AllCocktails>> = MutableLiveData()

    fun getCocktailByName(name: String) = viewModelScope.launch {
        cocktails.postValue(Resource.Loading())
        val response = cocktailsRepository.getCocktailByName(name)
        cocktails.postValue(handleSearchCocktailResponse(response))
    }

    private fun handleSearchCocktailResponse(response: Response<AllCocktails>): Resource<AllCocktails> {
        if (response.isSuccessful) response.body()?.let { result ->
            return Resource.Success(result)
        }
        return Resource.Error(response.message())
    }
}