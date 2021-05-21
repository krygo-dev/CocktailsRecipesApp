package com.krygodev.coctailsrecipesapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "ingredients"
)
data class Ingredient(
    @PrimaryKey
    val strIngredient1: String,
    var inStock: Boolean = false
)