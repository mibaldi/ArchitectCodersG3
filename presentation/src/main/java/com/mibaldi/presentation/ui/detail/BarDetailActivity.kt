package com.mibaldi.presentation.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mibaldi.presentation.R
import com.mibaldi.presentation.databinding.ActivityDetailBinding
import com.mibaldi.presentation.utils.getViewModel
import com.mibaldi.presentation.utils.snack
import kotlinx.android.synthetic.main.activity_detail.*

class BarDetailActivity : AppCompatActivity() {
    companion object {
        const val BEER = "BarDetailActivity:beer"
    }

    private lateinit var viewModel: BarDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel { BarDetailViewModel(intent.getParcelableExtra(BEER)) }

        val binding: ActivityDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail)

        binding.model = viewModel
        binding.lifecycleOwner = this
        setSupportActionBar(barDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { it.snack("Not implemented yet") }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
