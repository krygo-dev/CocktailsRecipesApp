package com.krygodev.coctailsrecipesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.data.Category
import com.krygodev.coctailsrecipesapp.data.Ingredient
import com.krygodev.coctailsrecipesapp.ui.fragments.IngredientsFragment
import kotlinx.android.synthetic.main.card_view_ingredient.view.*

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    inner class IngredientsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Ingredient>() {
        override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem.strIngredient1 == newItem.strIngredient1
        }

        override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_view_ingredient,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Ingredient) -> Unit)? = null

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredient = differ.currentList[position]
        holder.itemView.apply {
            ingredientNameTextView.text = ingredient.strIngredient1
            ingredientInStockCheckBox.isChecked = ingredient.inStock
            ingredientInStockCheckBox.setOnClickListener {
                onItemClickListener?.let {
                    it(ingredient)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (Ingredient) -> Unit) {
        onItemClickListener = listener
    }
}