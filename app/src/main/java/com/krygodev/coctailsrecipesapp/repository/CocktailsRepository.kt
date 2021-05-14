package com.krygodev.coctailsrecipesapp.repository

import com.krygodev.coctailsrecipesapp.api.RetrofitInstance
import com.krygodev.coctailsrecipesapp.data.AllCategories
import com.krygodev.coctailsrecipesapp.db.CocktailsDatabase
import retrofit2.Response

class CocktailsRepository(
    val db: CocktailsDatabase
) {
    suspend fun getAllCategories() = RetrofitInstance.api.getAllCategories()

    suspend fun getCocktailsFromCategory(category: String) = RetrofitInstance.api.getCocktailsFromCategory(category)
}