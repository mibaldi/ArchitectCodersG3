package com.mibaldi.presentation.ui.detail.beer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mibaldi.presentation.R
import com.mibaldi.presentation.base.activities.BaseActivity
import com.mibaldi.presentation.databinding.ActivityBeerBinding
import com.mibaldi.presentation.ui.adapters.BeerAdapter
import kotlinx.android.synthetic.main.activity_beer.*
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class BeerListActivity : BaseActivity() {
    companion object {
        const val BEER_SELECTED = "BeerListActivity:beer"
    }

    private val adapter by lazy {
        BeerAdapter {
            val intent = Intent()
            intent.putExtra(BEER_SELECTED, it)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
    private val viewModel: BeerListViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityBeerBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_beer)
        binding.model = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(barDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recycler.adapter = adapter
        viewModel.post.observe(this, Observer { adapter.submitList(it) })
    }


}