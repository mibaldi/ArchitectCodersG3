package com.mibaldi.presentation.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.google.firebase.firestore.FirebaseFirestore
import com.mibaldi.data.repository.FirestoreSimpleRepository
import com.mibaldi.domain.interactors.bar.GetBarInteractor
import com.mibaldi.presentation.R
import com.mibaldi.presentation.base.activities.BaseActivity
import com.mibaldi.presentation.databinding.ActivityMainBinding
import com.mibaldi.presentation.framework.datasources.FirestoreSimpleDataSource
import com.mibaldi.presentation.ui.adapters.BarAdapter
import com.mibaldi.presentation.ui.common.Navigator
import com.mibaldi.presentation.utils.getViewModel


class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: BarAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firestoreDataSource =
            FirestoreSimpleDataSource(FirebaseFirestore.getInstance())
        val repository = FirestoreSimpleRepository(firestoreDataSource)
        val getBarInteractor = GetBarInteractor(repository)
        val navigator = Navigator(this)
        viewModel = getViewModel { MainViewModel(getBarInteractor, navigator) }


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
