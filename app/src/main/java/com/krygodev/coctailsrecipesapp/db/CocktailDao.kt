package com.krygodev.coctailsrecipesapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.krygodev.coctailsrecipesapp.data.Cocktail

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cocktail: Cocktail): Long

    @Query("SELECT * FROM cocktails")
    fun getAllCocktails(): LiveData<List<Cocktail>>

    @Delete
    suspend fun delete(cocktail: Cocktail)
}