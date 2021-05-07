package com.krygodev.coctailsrecipesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.krygodev.coctailsrecipesapp.data.Cocktail
import com.krygodev.coctailsrecipesapp.data.Ingredient

@Database(
    entities = [Cocktail::class, Ingredient::class],
    version = 1
)
abstract class CocktailsDatabase : RoomDatabase() {

    abstract fun getCocktailDao(): CocktailDao
    abstract fun getIngredientDao(): IngredientDao

    companion object {
        @Volatile
        private var instance: CocktailsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CocktailsDatabase::class.java,
                "article_db.db"
            ).build()
    }
}