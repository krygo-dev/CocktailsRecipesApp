package com.krygodev.coctailsrecipesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.data.CocktailFromCategory
import kotlinx.android.synthetic.main.card_view_cocktail.view.*

class CategoryCocktailsAdapter : RecyclerView.Adapter<CategoryCocktailsAdapter.CategoryCocktailsViewHolder>() {

    inner class CategoryCocktailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<CocktailFromCategory>() {
        override fun areItemsTheSame(
            oldItem: CocktailFromCategory,
            newItem: CocktailFromCategory
        ): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(
            oldItem: CocktailFromCategory,
            newItem: CocktailFromCategory
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryCocktailsViewHolder {
        return CategoryCocktailsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_view_cocktail,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryCocktailsViewHolder, position: Int) {
        val cocktail = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(cocktail.strDrinkThumb).into(cocktailImageImageView)
            cocktailNameTextView.text = cocktail.strDrink
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}