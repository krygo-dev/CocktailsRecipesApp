package com.krygodev.coctailsrecipesapp.api

import com.krygodev.coctailsrecipesapp.data.AllCategories
import com.krygodev.coctailsrecipesapp.data.AllCocktails
import com.krygodev.coctailsrecipesapp.data.AllCocktailsFromCategory
import com.krygodev.coctailsrecipesapp.data.AllIngredients
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailsAPI {

    @GET("api/json/v1/1/list.php")
    suspend fun getAllCategories(
        @Query("c")
        category: String = "list"
    ): Response<AllCategories>

    @GET("api/json/v1/1//filter.php")
    suspend fun getCocktailsFromCategory(
        @Query("c")
        category: String
    ): Response<AllCocktailsFromCategory>

    @GET("api/json/v1/1/random.php")
    suspend fun getRandomCocktail(): Response<AllCocktails>

    @GET("api/json/v1/1/search.php")
    suspend fun getCocktailByName(
        @Query("s")
        cocktailName: String
    ): Response<AllCocktails>

    @GET("api/json/v1/1/lookup.php")
    suspend fun getCocktailByID(
        @Query("i")
        cocktailID: Int
    ): Response<AllCocktails>

    @GET("api/json/v1/1/list.php")
    suspend fun getAllIngredients(
        @Query("i")
        ingredients: String = "list"
    ): Response<AllIngredients>
}