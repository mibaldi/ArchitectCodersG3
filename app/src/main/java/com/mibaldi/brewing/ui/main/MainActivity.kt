package com.mibaldi.brewing.ui.main

import android.os.Bundle
import android.view.View
import com.mibaldi.brewing.R
import com.mibaldi.brewing.base.activities.BaseActivity
import com.mibaldi.brewing.interactors.BeerInteractor
import com.mibaldi.brewing.repositories.FirestoreDB
import com.mibaldi.brewing.ui.adapters.PubsAdapter
import com.mibaldi.brewing.ui.detail.DetailActivity
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
    private lateinit var adapter: PubsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMock()
        val beerInteractor = BeerInteractor(this)
        viewModel = withViewModel({MainViewModel(beerInteractor)}){
            observe(model,::updateUI)
        }
        adapter = PubsAdapter(viewModel::onPubClicked)
        recycler.adapter = adapter
    }

    fun initMock(){
        val context =  this
        GlobalScope.launch {
            FirestoreDB.json = withContext(Dispatchers.IO) {FirestoreDB.loadJSONFromAsset(context)}
            FirestoreDB.addBars()
        }


    }

    private fun updateUI(model: MainViewModel.UiModel) {
        progress.visibility = if (model is MainViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is MainViewModel.UiModel.Content -> adapter.beers = model.beers
            is MainViewModel.UiModel.Navigation -> startActivity<DetailActivity> {
                putExtra(DetailActivity.PUB, model.beer)
            }
        }

    }
}
