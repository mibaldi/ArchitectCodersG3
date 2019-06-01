package com.mibaldi.presentation.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.mibaldi.presentation.R
import com.mibaldi.presentation.base.activities.BaseActivity
import com.mibaldi.presentation.datasources.FirestoreSimpleDataSource
import com.mibaldi.data.repository.FirestoreSimpleRepository
import com.mibaldi.presentation.ui.adapters.BarAdapter
import com.mibaldi.presentation.ui.detail.BarDetailActivity
import com.mibaldi.presentation.utils.observe
import com.mibaldi.presentation.utils.startActivity
import com.mibaldi.presentation.utils.withViewModel
import com.mibaldi.domain.interactors.bar.GetBarSimpleInteractor
import com.mibaldi.presentation.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_main.*






class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: BarAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val firestoreDataSource = FirestoreSimpleDataSource(FirebaseFirestore.getInstance())
        val repository = FirestoreSimpleRepository(firestoreDataSource)
        val getBarInteractor = GetBarSimpleInteractor(repository)
        viewModel = withViewModel({ MainViewModel(getBarInteractor) }) {
            observe(model, ::updateUI)
        }
        adapter = BarAdapter(viewModel::onBarClicked)
        recycler.adapter = adapter
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
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI(model: MainViewModel.UiModel) {
        progress.visibility = if (model is MainViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is MainViewModel.UiModel.Content -> adapter.bars = model.bars
            is MainViewModel.UiModel.Navigation -> startActivity<BarDetailActivity> {
                putExtra(BarDetailActivity.BEER, model.bar)
            }
            is MainViewModel.UiModel.NavigationProfile -> startActivity<ProfileActivity> {}
        }

    }
}
