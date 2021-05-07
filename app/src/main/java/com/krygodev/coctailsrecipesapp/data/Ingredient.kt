package com.krygodev.coctailsrecipesapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "ingredients"
)
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val strIngredient1: String,
    var inStock: Boolean = false
)