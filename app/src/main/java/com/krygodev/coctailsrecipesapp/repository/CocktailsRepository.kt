package com.krygodev.coctailsrecipesapp.repository

import com.krygodev.coctailsrecipesapp.api.RetrofitInstance
import com.krygodev.coctailsrecipesapp.data.Cocktail
import com.krygodev.coctailsrecipesapp.data.Ingredient
import com.krygodev.coctailsrecipesapp.db.CocktailsDatabase

class CocktailsRepository(
    val db: CocktailsDatabase
) {
    suspend fun getAllCategories() = RetrofitInstance.api.getAllCategories()

    suspend fun getCocktailsFromCategory(category: String) = RetrofitInstance.api.getCocktailsFromCategory(category)

    suspend fun getRandomCocktail() = RetrofitInstance.api.getRandomCocktail()

    suspend fun getCocktailById(id: Int) = RetrofitInstance.api.getCocktailByID(id)

    suspend fun getAllIngredients() = RetrofitInstance.api.getAllIngredients()

    suspend fun insertIngredient(ingredient: Ingredient) = db.getIngredientDao().insert(ingredient)

    suspend fun deleteIngredient(ingredient: Ingredient) = db.getIngredientDao().delete(ingredient)

    fun getIngredientsFromDatabase() = db.getIngredientDao().getAllIngredients()

    suspend fun insertCocktail(cocktail: Cocktail) = db.getCocktailDao().insert(cocktail)

    suspend fun deleteCocktail(cocktail: Cocktail) = db.getCocktailDao().delete(cocktail)

    fun getCocktailsFromDatabase() = db.getCocktailDao().getAllCocktails()
}