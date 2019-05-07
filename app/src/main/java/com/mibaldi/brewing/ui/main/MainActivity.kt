package com.mibaldi.brewing.ui.main

import android.os.Bundle
import android.view.View
import com.mibaldi.brewing.R
import com.mibaldi.brewing.base.activities.BaseActivity
import com.mibaldi.brewing.interactors.BeerInteractor
import com.mibaldi.brewing.repositories.FirestoreDB
import com.mibaldi.brewing.ui.adapters.BeersAdapter
import com.mibaldi.brewing.ui.detail.DetailActivity
import com.mibaldi.brewing.utils.loadJSONFromAsset
import com.mibaldi.brewing.utils.observe
import com.mibaldi.brewing.utils.startActivity
import com.mibaldi.brewing.utils.withViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: BeersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val beerInteractor = BeerInteractor(this)
        viewModel = withViewModel({MainViewModel(beerInteractor)}){
            observe(model,::updateUI)
        }
        adapter = BeersAdapter(viewModel::onPubClicked)
        recycler.adapter = adapter
    }



    private fun updateUI(model: MainViewModel.UiModel) {
        progress.visibility = if (model is MainViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is MainViewModel.UiModel.Content -> adapter.beers = model.beers
            is MainViewModel.UiModel.Navigation -> startActivity<DetailActivity> {
                putExtra(DetailActivity.BEER, model.beer)
            }
        }

    }
}
