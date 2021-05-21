package com.krygodev.coctailsrecipesapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.krygodev.coctailsrecipesapp.R
import com.krygodev.coctailsrecipesapp.data.Cocktail
import com.krygodev.coctailsrecipesapp.data.Ingredient
import kotlinx.android.synthetic.main.cocktail_details.view.*

class CocktailDetailsAdapter : RecyclerView.Adapter<CocktailDetailsAdapter.CocktailDetailsViewHolder>() {

    var ingInStock: List<Ingredient> = mutableListOf()
    var cocktailsInFav: List<Cocktail> = mutableListOf()

    private val ingredientInStockColor: Int = Color.rgb(14, 171, 0)

    inner class CocktailDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Cocktail>() {
        override fun areItemsTheSame(
            oldItem: Cocktail,
            newItem: Cocktail
        ): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(
            oldItem: Cocktail,
            newItem: Cocktail
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailDetailsViewHolder {
        return CocktailDetailsViewHolder(
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

    override fun onBindViewHolder(holder: CocktailDetailsViewHolder, position: Int) {
        val cocktail = differ.currentList[position]

        cocktail.inStock = cocktailsInFav.any{ c -> c.idDrink == cocktail.idDrink}

        holder.itemView.apply {
            Glide.with(this).load(cocktail.strDrinkThumb).into(cocktailImageView)
            cocktailNameTextView.text = cocktail.strDrink
            cocktailInstructionTextView.text = cocktail.strInstructions

            if (cocktail.strIngredient1 != null) {
                ingredientName1TextView.text = cocktail.strIngredient1
                ingredientMeasure1TextView.text = cocktail.strMeasure1

                if (ingInStock.any{ ing -> ing.strIngredient1 == cocktail.strIngredient1}) {
                    ingredientName1TextView.setTextColor(ingredientInStockColor)
                    ingredientMeasure1TextView.setTextColor(ingredientInStockColor)
                }
            } else {
                tableRow1.visibility = View.GONE
            }

            if (cocktail.strIngredient2 != null) {
                ingredientName2TextView.text = cocktail.strIngredient2
                ingredientMeasure2TextView.text = cocktail.strMeasure2

                if (ingInStock.any{ ing -> ing.strIngredient1 == cocktail.strIngredient2}) {
                    ingredientName2TextView.setTextColor(ingredientInStockColor)
                    ingredientMeasure2TextView.setTextColor(ingredientInStockColor)
                }
            } else {
                tableRow2.visibility = View.GONE
            }

            if (cocktail.strIngredient3 != null) {
                ingredientName3TextView.text = cocktail.strIngredient3
                ingredientMeasure3TextView.text = cocktail.strMeasure3

                if (ingInStock.any{ ing -> ing.strIngredient1 == cocktail.strIngredient3}) {
                    ingredientName3TextView.setTextColor(ingredientInStockColor)
                    ingredientMeasure3TextView.setTextColor(ingredientInStockColor)
                }
            } else {
                tableRow3.visibility = View.GONE
            }

            if (cocktail.strIngredient4 != null) {
                ingredientName4TextView.text = cocktail.strIngredient4
                ingredientMeasure4TextView.text = cocktail.strMeasure4

                if (ingInStock.any{ ing -> ing.strIngredient1 == cocktail.strIngredient4}) {
                    ingredientName4TextView.setTextColor(ingredientInStockColor)
                    ingredientMeasure4TextView.setTextColor(ingredientInStockColor)
                }
            } else {
                tableRow4.visibility = View.GONE
            }

            if (cocktail.strIngredient5 != null) {
                ingredientName5TextView.text = cocktail.strIngredient5
                ingredientMeasure5TextView.text = cocktail.strMeasure5

                if (ingInStock.any{ ing -> ing.strIngredient1 == cocktail.strIngredient5}) {
                    ingredientName5TextView.setTextColor(ingredientInStockColor)
                    ingredientMeasure5TextView.setTextColor(ingredientInStockColor)
                }
            } else {
                tableRow5.visibility = View.GONE
            }

            if (cocktail.strIngredient6 != null) {
                ingredientName6TextView.text = cocktail.strIngredient6
                ingredientMeasure6TextView.text = cocktail.strMeasure6

                if (ingInStock.any{ ing -> ing.strIngredient1 == cocktail.strIngredient6}) {
                    ingredientName6TextView.setTextColor(ingredientInStockColor)
                    ingredientMeasure6TextView.setTextColor(ingredientInStockColor)
                }
            } else {
                tableRow6.visibility = View.GONE
            }

            if (cocktail.strIngredient7 != null) {
                ingredientName7TextView.text = cocktail.strIngredient7
                ingredientMeasure7TextView.text = cocktail.strMeasure7

                if (ingInStock.any{ ing -> ing.strIngredient1 == cocktail.strIngredient7}) {
                    ingredientName7TextView.setTextColor(ingredientInStockColor)
                    ingredientMeasure7TextView.setTextColor(ingredientInStockColor)
                }
            } else {
                tableRow7.visibility = View.GONE
            }

            if (cocktail.strIngredient8 != null) {
                ingredientName8TextView.text = cocktail.strIngredient8
                ingredientMeasure8TextView.text = cocktail.strMeasure8

                if (ingInStock.any{ ing -> ing.strIngredient1 == cocktail.strIngredient8}) {
                    ingredientName8TextView.setTextColor(ingredientInStockColor)
                    ingredientMeasure8TextView.setTextColor(ingredientInStockColor)
                }
            } else {
                tableRow8.visibility = View.GONE
            }

            if (cocktail.strIngredient9 != null) {
                ingredientName9TextView.text = cocktail.strIngredient9
                ingredientMeasure9TextView.text = cocktail.strMeasure9

                if (ingInStock.any{ ing -> ing.strIngredient1 == cocktail.strIngredient9}) {
                    ingredientName9TextView.setTextColor(ingredientInStockColor)
                    ingredientMeasure9TextView.setTextColor(ingredientInStockColor)
                }
            } else {
                tableRow9.visibility = View.GONE
            }

            if (cocktail.strIngredient10 != null) {
                ingredientName10TextView.text = cocktail.strIngredient10
                ingredientMeasure10TextView.text = cocktail.strMeasure10

                if (ingInStock.any{ ing -> ing.strIngredient1 == cocktail.strIngredient10}) {
                    ingredientName10TextView.setTextColor(ingredientInStockColor)
                    ingredientMeasure10TextView.setTextColor(ingredientInStockColor)
                }
            } else {
                tableRow10.visibility = View.GONE
            }

            if (cocktail.strIngredient11 != null) {
                ingredientName11TextView.text = cocktail.strIngredient11
                ingredientMeasure11TextView.text = cocktail.strMeasure11

                if (ingInStock.any{ ing -> ing.strIngredient1 == cocktail.strIngredient11}) {
                    ingredientName11TextView.setTextColor(ingredientInStockColor)
                    ingredientMeasure11TextView.setTextColor(ingredientInStockColor)
                }
            } else {
                tableRow11.visibility = View.GONE
            }

            if (cocktail.strIngredient12 != null) {
                ingredientName12TextView.text = cocktail.strIngredient12
                ingredientMeasure12TextView.text = cocktail.strMeasure12

                if (ingInStock.any{ ing -> ing.strIngredient1 == cocktail.strIngredient12}) {
                    ingredientName12TextView.setTextColor(ingredientInStockColor)
                    ingredientMeasure12TextView.setTextColor(ingredientInStockColor)
                }
            } else {
                tableRow12.visibility = View.GONE
            }

            if (cocktail.strIngredient13 != null) {
                ingredientName13TextView.text = cocktail.strIngredient13
                ingredientMeasure13TextView.text = cocktail.strMeasure13

                if (ingInStock.any{ ing -> ing.strIngredient1 == cocktail.strIngredient13}) {
                    ingredientName13TextView.setTextColor(ingredientInStockColor)
                    ingredientMeasure13TextView.setTextColor(ingredientInStockColor)
                }
            } else {
                tableRow13.visibility = View.GONE
            }

            if (cocktail.strIngredient14 != null) {
                ingredientName14TextView.text = cocktail.strIngredient14
                ingredientMeasure14TextView.text = cocktail.strMeasure14

                if (ingInStock.any{ ing -> ing.strIngredient1 == cocktail.strIngredient14}) {
                    ingredientName14TextView.setTextColor(ingredientInStockColor)
                    ingredientMeasure14TextView.setTextColor(ingredientInStockColor)
                }
            } else {
                tableRow14.visibility = View.GONE
            }

            if (cocktail.strIngredient15 != null) {
                ingredientName15TextView.text = cocktail.strIngredient15
                ingredientMeasure15TextView.text = cocktail.strMeasure15

                if (ingInStock.any{ ing -> ing.strIngredient1 == cocktail.strIngredient15}) {
                    ingredientName15TextView.setTextColor(ingredientInStockColor)
                    ingredientMeasure15TextView.setTextColor(ingredientInStockColor)
                }
            } else {
                tableRow15.visibility = View.GONE
            }

            if (cocktail.inStock) cocktailInFavImageView.setBackgroundResource(R.drawable.ic_favourite_full)
            else cocktailInFavImageView.setBackgroundResource(R.drawable.ic_favourite)

            cocktailInFavImageView.setOnClickListener {
                onItemClickListener?.let {
                    cocktail.inStock = !cocktail.inStock
                    if (cocktail.inStock) cocktailInFavImageView.setBackgroundResource(R.drawable.ic_favourite_full)
                    else cocktailInFavImageView.setBackgroundResource(R.drawable.ic_favourite)
                    it(cocktail)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (Cocktail) -> Unit) {
        onItemClickListener = listener
    }
}