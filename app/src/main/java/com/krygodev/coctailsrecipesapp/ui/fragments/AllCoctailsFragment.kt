package com.krygodev.coctailsrecipesapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.krygodev.coctailsrecipesapp.R
import kotlinx.android.synthetic.main.fragment_all_coctails.*

class AllCoctailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_coctails, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allCoctailsSearchView.apply {
            onActionViewExpanded()
            clearFocus()
        }
    }
}