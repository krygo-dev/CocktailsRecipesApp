package com.krygodev.coctailsrecipesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.data.Cocktail
import kotlinx.android.synthetic.main.card_view_ingredient.view.*
import kotlinx.android.synthetic.main.card_view_ingredient.view.ingredientNameTextView
import kotlinx.android.synthetic.main.cocktail_details_ingredient.view.*

class RandomCocktailAdapter : RecyclerView.Adapter<RandomCocktailAdapter.RandomCocktailViewHolder>() {

    inner class RandomCocktailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Cocktail>() {
        override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
            return oldItem.strIngredient1 == newItem.strIngredient1
        }

        override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomCocktailViewHolder {
        return RandomCocktailViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cocktail_details_ingredient,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RandomCocktailViewHolder, position: Int) {
        val cocktail = differ.currentList[position]
        holder.itemView.apply {
            ingredientNameTextView.text = cocktail.strIngredient1
            ingredientMeasureTextView.text = cocktail.strMeasure1
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}