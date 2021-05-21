package com.krygodev.coctailsrecipesapp.di

import android.content.Context
import androidx.room.Room
import com.krygodev.coctailsrecipesapp.api.CocktailsAPI
import com.krygodev.coctailsrecipesapp.db.CocktailsDatabase
import com.krygodev.coctailsrecipesapp.util.Constants.BASE_URL
import com.krygodev.coctailsrecipesapp.util.Constants.COCKTAILS_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCocktailsDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        CocktailsDatabase::class.java,
        COCKTAILS_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideCocktailDao(db: CocktailsDatabase) = db.getCocktailDao()

    @Singleton
    @Provides
    fun provideIngredientDao(db: CocktailsDatabase) = db.getIngredientDao()

    @Singleton
    @Provides
    fun provideCocktailsAPI() : CocktailsAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CocktailsAPI::class.java)
    }
}