package com.mibaldi.data.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.mibaldi.data.R
import com.mibaldi.data.utils.getViewModel
import com.mibaldi.data.utils.loadUrl
import com.mibaldi.data.utils.snack
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
