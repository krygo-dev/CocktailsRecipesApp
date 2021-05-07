package com.krygodev.coctailsrecipesapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.db.CocktailsDatabase
import com.krygodev.coctailsrecipesapp.repository.CocktailsRepository
import kotlinx.android.synthetic.main.activity_startup.*

class StartupActivity : AppCompatActivity() {

    lateinit var repository: CocktailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        val navController = findNavController(R.id.fragmentContainer)
        bottomNavigationView.setupWithNavController(navController)

        repository = CocktailsRepository(CocktailsDatabase(this))
    }
}