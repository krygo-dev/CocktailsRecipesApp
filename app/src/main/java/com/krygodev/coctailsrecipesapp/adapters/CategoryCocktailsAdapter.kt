package com.krygodev.coctailsrecipesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.data.CocktailFromCategory
import kotlinx.android.synthetic.main.card_view_cocktail.view.*
import java.util.*
import kotlin.collections.ArrayList

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

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((CocktailFromCategory) -> Unit)? = null

    override fun onBindViewHolder(holder: CategoryCocktailsViewHolder, position: Int) {
        val cocktail = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(cocktail.strDrinkThumb).into(cocktailImageImageView)
            cocktailNameTextView.text = cocktail.strDrink

            setOnClickListener {
                onItemClickListener?.let { it(cocktail) }
            }
        }
    }

    fun setOnItemClickListener(listener: (CocktailFromCategory) -> Unit) {
        onItemClickListener = listener
    }

    /**override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(text: CharSequence?): FilterResults {
                val filteredList = ArrayList<CocktailFromCategory>()

                if (text == null || text.isEmpty()){
                    filteredList.addAll(listOfAll)
                } else {
                    val pattern = text.toString().toLowerCase(Locale.ROOT).trim()

                    for (cocktail in listOfAll) {
                        if (cocktail.strDrink.toLowerCase(Locale.ROOT).contains(pattern)) filteredList.add(cocktail)
                    }
                }

                val filterResult = FilterResults()
                filterResult.values = filteredList
                return filterResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(text: CharSequence?, result: FilterResults?) {
                differ.submitList(result?.values as MutableList<CocktailFromCategory>?)
            }

        }
    }**/
}