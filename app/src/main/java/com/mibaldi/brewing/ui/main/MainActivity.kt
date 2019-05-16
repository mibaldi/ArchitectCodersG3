package com.mibaldi.brewing.ui.main

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.mibaldi.brewing.R
import com.mibaldi.brewing.base.activities.BaseActivity
import com.mibaldi.brewing.interactors.GetBarInteractor.GetBarInteractorImpl
import com.mibaldi.brewing.interactors.GetCurrentUserInteractor.GetCurrentUserInteractorImpl
import com.mibaldi.brewing.ui.adapters.BarAdapter
import com.mibaldi.brewing.ui.detail.BarDetailActivity
import com.mibaldi.brewing.utils.observe
import com.mibaldi.brewing.utils.startActivity
import com.mibaldi.brewing.utils.withViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: BarAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val getBarInteractor = GetBarInteractorImpl()
        val getCurrentUserInteractor = GetCurrentUserInteractorImpl()
        viewModel = withViewModel({MainViewModel(getBarInteractor,getCurrentUserInteractor)}){
            observe(model,::updateUI)
        }
        adapter = BarAdapter(viewModel::onBarClicked)
        recycler.adapter = adapter
    }

    private fun showCurrentUser(user: String){
        Snackbar.make(clMain,user,Snackbar.LENGTH_INDEFINITE).show()
    }



    private fun updateUI(model: MainViewModel.UiModel) {
        progress.visibility = if (model is MainViewModel.UiModel.Loading) View.VISIBLE else View.GONE
        when (model) {
            is MainViewModel.UiModel.Content -> adapter.bars = model.bars
            is MainViewModel.UiModel.Navigation -> startActivity<BarDetailActivity> {
                putExtra(BarDetailActivity.BEER, model.bar)
            }
            is MainViewModel.UiModel.CurrentUser -> showCurrentUser(model.user)
        }

    }
}
