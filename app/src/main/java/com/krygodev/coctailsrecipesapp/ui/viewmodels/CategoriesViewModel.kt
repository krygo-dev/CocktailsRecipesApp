package com.krygodev.coctailsrecipesapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krygodev.coctailsrecipesapp.data.AllCategories
import com.krygodev.coctailsrecipesapp.repository.CocktailsRepository
import com.krygodev.coctailsrecipesapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class CategoriesViewModel(
    val cocktailsRepository: CocktailsRepository
) : ViewModel() {

    val categories: MutableLiveData<Resource<AllCategories>> = MutableLiveData()

    init {
        getAllCategories()
    }

    fun getAllCategories() = viewModelScope.launch {
        categories.postValue(Resource.Loading())
        val response = cocktailsRepository.getAllCategories()
        categories.postValue(handleAllCategoriesResponse(response))
    }

    private fun handleAllCategoriesResponse(response: Response<AllCategories>) : Resource<AllCategories> {
        if (response.isSuccessful) response.body()?.let { result ->
            return Resource.Success(result)
        }
        return Resource.Error(response.message())
    }
}