package com.mibaldi.presentation.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.mibaldi.presentation.R
import com.mibaldi.presentation.base.activities.BaseActivity
import com.mibaldi.presentation.databinding.ActivityMainBinding
import com.mibaldi.presentation.ui.adapters.BarAdapter
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by currentScope.viewModel(this) { parametersOf(this@MainActivity) }

    private lateinit var adapter: BarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.model = viewModel
        binding.lifecycleOwner = this

        adapter = BarAdapter(viewModel::onBarClicked)
        binding.recycler.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menuProfile -> {
                viewModel.goToProfile()
                true
            }
            R.id.menuMap -> {
                viewModel.goToMap()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
