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
import kotlinx.android.synthetic.main.cocktail_details.view.*

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
                R.layout.cocktail_details,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Cocktail) -> Unit)? = null

    override fun onBindViewHolder(holder: RandomCocktailViewHolder, position: Int) {
        val cocktail = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(cocktail.strDrinkThumb).into(cocktailImageView)
            cocktailNameTextView.text = cocktail.strDrink
            cocktailInstructionTextView.text = cocktail.strInstructions

            if (cocktail.strIngredient1 != null) {
                ingredientName1TextView.text = cocktail.strIngredient1
                ingredientMeasure1TextView.text = cocktail.strMeasure1
            } else {
                tableRow1.visibility = View.GONE
            }

            if (cocktail.strIngredient2 != null) {
                ingredientName2TextView.text = cocktail.strIngredient2
                ingredientMeasure2TextView.text = cocktail.strMeasure2
            } else {
                tableRow2.visibility = View.GONE
            }

            if (cocktail.strIngredient3 != null) {
                ingredientName3TextView.text = cocktail.strIngredient3
                ingredientMeasure3TextView.text = cocktail.strMeasure3
            } else {
                tableRow3.visibility = View.GONE
            }

            if (cocktail.strIngredient4 != null) {
                ingredientName4TextView.text = cocktail.strIngredient4
                ingredientMeasure4TextView.text = cocktail.strMeasure4
            } else {
                tableRow4.visibility = View.GONE
            }

            if (cocktail.strIngredient5 != null) {
                ingredientName5TextView.text = cocktail.strIngredient5
                ingredientMeasure5TextView.text = cocktail.strMeasure5
            } else {
                tableRow5.visibility = View.GONE
            }

            if (cocktail.strIngredient6 != null) {
                ingredientName6TextView.text = cocktail.strIngredient6
                ingredientMeasure6TextView.text = cocktail.strMeasure6
            } else {
                tableRow6.visibility = View.GONE
            }

            if (cocktail.strIngredient7 != null) {
                ingredientName7TextView.text = cocktail.strIngredient7
                ingredientMeasure7TextView.text = cocktail.strMeasure7
            } else {
                tableRow7.visibility = View.GONE
            }

            if (cocktail.strIngredient8 != null) {
                ingredientName8TextView.text = cocktail.strIngredient8
                ingredientMeasure8TextView.text = cocktail.strMeasure8
            } else {
                tableRow8.visibility = View.GONE
            }

            if (cocktail.strIngredient9 != null) {
                ingredientName9TextView.text = cocktail.strIngredient9
                ingredientMeasure9TextView.text = cocktail.strMeasure9
            } else {
                tableRow9.visibility = View.GONE
            }

            if (cocktail.strIngredient10 != null) {
                ingredientName10TextView.text = cocktail.strIngredient10
                ingredientMeasure10TextView.text = cocktail.strMeasure10
            } else {
                tableRow10.visibility = View.GONE
            }

            if (cocktail.strIngredient11 != null) {
                ingredientName11TextView.text = cocktail.strIngredient11
                ingredientMeasure11TextView.text = cocktail.strMeasure11
            } else {
                tableRow11.visibility = View.GONE
            }

            if (cocktail.strIngredient12 != null) {
                ingredientName12TextView.text = cocktail.strIngredient12
                ingredientMeasure12TextView.text = cocktail.strMeasure12
            } else {
                tableRow12.visibility = View.GONE
            }

            if (cocktail.strIngredient13 != null) {
                ingredientName13TextView.text = cocktail.strIngredient13
                ingredientMeasure13TextView.text = cocktail.strMeasure13
            } else {
                tableRow13.visibility = View.GONE
            }

            if (cocktail.strIngredient14 != null) {
                ingredientName14TextView.text = cocktail.strIngredient14
                ingredientMeasure14TextView.text = cocktail.strMeasure14
            } else {
                tableRow14.visibility = View.GONE
            }

            if (cocktail.strIngredient15 != null) {
                ingredientName15TextView.text = cocktail.strIngredient15
                ingredientMeasure15TextView.text = cocktail.strMeasure15
            } else {
                tableRow15.visibility = View.GONE
            }

            cocktailInFavImageView.setOnClickListener { view ->
                onItemClickListener?.let {
                    cocktail.inStock = true
                    view.setBackgroundResource(R.drawable.ic_favourite_full)
                    it(cocktail)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (Cocktail) -> Unit) {
        onItemClickListener = listener
    }
}
