package com.krygodev.coctailsrecipesapp.ui.viewmodels

import androidx.lifecycle.*
import com.krygodev.coctailsrecipesapp.data.AllCocktails
import com.krygodev.coctailsrecipesapp.repository.CocktailsRepository
import com.krygodev.coctailsrecipesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchCocktailViewModel @Inject constructor(
    val cocktailsRepository: CocktailsRepository
) : ViewModel() {

    var cocktails: MutableLiveData<Resource<AllCocktails>> = MutableLiveData()

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