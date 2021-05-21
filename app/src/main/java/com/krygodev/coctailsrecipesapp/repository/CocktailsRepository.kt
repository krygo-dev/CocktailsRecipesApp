package com.krygodev.coctailsrecipesapp.repository

import com.krygodev.coctailsrecipesapp.api.CocktailsAPI
import com.krygodev.coctailsrecipesapp.data.Cocktail
import com.krygodev.coctailsrecipesapp.data.Ingredient
import com.krygodev.coctailsrecipesapp.db.CocktailDao
import com.krygodev.coctailsrecipesapp.db.IngredientDao
import javax.inject.Inject

class CocktailsRepository @Inject constructor(
    private val cocktailDao: CocktailDao,
    private val ingredientDao: IngredientDao,
    private val api: CocktailsAPI
) {
    suspend fun getAllCategories() = api.getAllCategories()

    suspend fun getCocktailsFromCategory(category: String) = api.getCocktailsFromCategory(category)

    suspend fun getRandomCocktail() = api.getRandomCocktail()

    suspend fun getCocktailById(id: Int) = api.getCocktailByID(id)

    suspend fun getCocktailByName(name: String) = api.getCocktailByName(name)

    suspend fun getAllIngredients() = api.getAllIngredients()

    suspend fun insertIngredient(ingredient: Ingredient) = ingredientDao.insert(ingredient)

    suspend fun deleteIngredient(ingredient: Ingredient) = ingredientDao.delete(ingredient)

    fun getIngredientsFromDatabase() = ingredientDao.getAllIngredients()

    suspend fun insertCocktail(cocktail: Cocktail) = cocktailDao.insert(cocktail)

    suspend fun deleteCocktail(cocktail: Cocktail) = cocktailDao.delete(cocktail)

    fun getCocktailsFromDatabase() = cocktailDao.getAllCocktails()

    //suspend fun getIngredientsList() = ingredientDao.getIngredientsList()
}