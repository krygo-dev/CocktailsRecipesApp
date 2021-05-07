package com.krygodev.coctailsrecipesapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.krygodev.coctailsrecipesapp.data.Ingredient

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredient: Ingredient): Long

    @Query("SELECT * FROM ingredients")
    fun getAllIngredients(): LiveData<List<Ingredient>>

    @Delete
    suspend fun delete(ingredient: Ingredient)
}