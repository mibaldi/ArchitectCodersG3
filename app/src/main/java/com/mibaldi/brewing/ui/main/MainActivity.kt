package com.mibaldi.brewing.ui.main

import android.os.Bundle
import android.view.View
import com.mibaldi.brewing.R
import com.mibaldi.brewing.base.activities.BaseActivity
import com.mibaldi.brewing.ui.detail.DetailActivity
import com.mibaldi.brewing.utils.observe
import com.mibaldi.brewing.utils.startActivity
import com.mibaldi.brewing.utils.withViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MoviesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = withViewModel({MainViewModel()}){
            observe(model,::updateUI)
        }
        adapter = MoviesAdapter(viewModel::onMovieClicked)
        recycler.adapter = adapter
    }

    private fun updateUI(model: MainViewModel.UiModel) {
        progress.visibility = if (model is MainViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is MainViewModel.UiModel.Content -> adapter.movies = model.pubs
            is MainViewModel.UiModel.Navigation -> startActivity<DetailActivity> {
                putExtra(DetailActivity.PUB, model.pub)
            }
        }

    }
}
