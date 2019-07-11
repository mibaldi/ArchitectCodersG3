package com.mibaldi.presentation.ui.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.mibaldi.presentation.R
import com.mibaldi.presentation.base.activities.BaseActivity
import com.mibaldi.presentation.databinding.ActivityProfileBinding
import kotlinx.android.synthetic.main.activity_profile.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileActivity : BaseActivity() {

    private val viewModel: ProfileViewModel by currentScope.viewModel(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityProfileBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_profile)

        viewModel.error.observe(this, Observer(::showError))

        binding.model = viewModel
        binding.lifecycleOwner = this

        profileToolbar.title = ""
        setSupportActionBar(profileToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menuRemove -> {
                viewModel.removeAccount()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showError(errorString: String) {
        Snackbar.make(coordinatorLayout, errorString, Snackbar.LENGTH_LONG).show()
    }
}
