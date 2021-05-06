package com.krygodev.coctailsrecipesapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.krygodev.coctailsrecipesapp.R

class CocktailDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cocktail_details)
    }
}