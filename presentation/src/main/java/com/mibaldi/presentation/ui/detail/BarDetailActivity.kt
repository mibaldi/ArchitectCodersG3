package com.mibaldi.presentation.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.mibaldi.presentation.R
import com.mibaldi.presentation.utils.getViewModel
import com.mibaldi.presentation.utils.loadUrl
import com.mibaldi.presentation.utils.snack
import kotlinx.android.synthetic.main.activity_detail.*

class BarDetailActivity : AppCompatActivity() {
    companion object {
        const val BEER = "BarDetailActivity:beer"
    }

    private lateinit var viewModel: BarDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(barDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = getViewModel { BarDetailViewModel(intent.getParcelableExtra(BEER)) }

        viewModel.model.observe(this, Observer(::updateUi))

        fab.setOnClickListener { it.snack("Not implemented yet") }
    }

    private fun updateUi(model: BarDetailViewModel.UiModel) = with(model.barView) {
        supportActionBar?.title = name
        barDetailImage.loadUrl(photo)
        barDetailSummary.text = description
    }
}
