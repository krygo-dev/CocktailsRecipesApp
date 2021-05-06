package com.krygodev.coctailsrecipesapp.api

import com.krygodev.coctailsrecipesapp.data.AllCocktails
import com.krygodev.coctailsrecipesapp.data.AllIngredients
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailsAPI {

    @GET("api/json/v1/1/search.php")
    suspend fun getCocktails(
        @Query("f")
        cocktailFirstLetter: String = "a"
    ): Response<AllCocktails>

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

    @GET("api/json/v1/1/list.php?i=list")
    suspend fun getAllIngredients(): Response<AllIngredients>
}