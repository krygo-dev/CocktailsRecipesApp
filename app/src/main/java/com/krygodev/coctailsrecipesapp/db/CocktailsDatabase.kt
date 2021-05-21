package com.krygodev.coctailsrecipesapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.krygodev.coctailsrecipesapp.data.Cocktail
import com.krygodev.coctailsrecipesapp.data.Ingredient

@Database(
    entities = [Cocktail::class, Ingredient::class],
    version = 5,
    exportSchema = false,
)
abstract class CocktailsDatabase : RoomDatabase() {

    abstract fun getCocktailDao(): CocktailDao
    abstract fun getIngredientDao(): IngredientDao

}