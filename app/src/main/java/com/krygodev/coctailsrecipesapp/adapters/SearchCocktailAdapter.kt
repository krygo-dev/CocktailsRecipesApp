package com.krygodev.coctailsrecipesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.data.Cocktail
import kotlinx.android.synthetic.main.card_view_cocktail.view.*

class SearchCocktailAdapter : RecyclerView.Adapter<SearchCocktailAdapter.SearchCocktailViewHolder>() {

    inner class SearchCocktailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Cocktail>() {
        override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
            return oldItem.strCategory == newItem.strCategory
        }

        override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCocktailViewHolder {
        return SearchCocktailViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_view_cocktail,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Cocktail) -> Unit)? = null

    override fun onBindViewHolder(holder: SearchCocktailViewHolder, position: Int) {
        val cocktail = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(cocktail.strDrinkThumb).into(cocktailImageImageView)
            cocktailNameTextView.text = cocktail.strDrink

            setOnClickListener {
                onItemClickListener?.let { it(cocktail) }
            }
        }
    }

    fun setOnItemClickListener(listener: (Cocktail) -> Unit) {
        onItemClickListener = listener
    }
}