package com.mibaldi.presentation.ui.detail

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.mibaldi.presentation.R
import com.mibaldi.presentation.base.activities.BaseActivity
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class BarDetailActivity : BaseActivity() {
    companion object {
        const val BEER = "BarDetailActivity:beer"
    }

    private val viewModel: BarDetailViewModel by currentScope.viewModel(this){ parametersOf(this@BarDetailActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail)

        binding.model = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(barDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel.setData(intent?.extras?.get(BEER) as BarView)
    }
}
