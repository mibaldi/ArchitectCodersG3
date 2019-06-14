package com.mibaldi.presentation.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.mibaldi.data.repository.LoginRepositoryImpl
import com.mibaldi.domain.entity.MyFirebaseUser
import com.mibaldi.domain.interactors.account.RemoveAccountInteractor
import com.mibaldi.domain.interactors.user.GetCurrentUserInteractor
import com.mibaldi.domain.interactors.login.SignOutInteractor
import com.mibaldi.presentation.R
import com.mibaldi.presentation.base.activities.BaseActivity
import com.mibaldi.presentation.databinding.ActivityProfileBinding
import com.mibaldi.presentation.framework.datasources.LoginDataSourceImpl
import com.mibaldi.presentation.ui.common.Navigator
import com.mibaldi.presentation.ui.login.EmailPasswordActivity
import com.mibaldi.presentation.ui.main.MainViewModel
import com.mibaldi.presentation.utils.loadUrl
import com.mibaldi.presentation.utils.observe
import com.mibaldi.presentation.utils.startActivity
import com.mibaldi.presentation.utils.withViewModel
import kotlinx.android.synthetic.main.activity_main.progress
import kotlinx.android.synthetic.main.activity_profile.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileActivity : BaseActivity() {

    private val viewModel: ProfileViewModel by currentScope.viewModel(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        profileToolbar.title = ""
        setSupportActionBar(profileToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel.model.observe(this, Observer(::updateUI))
        viewModel.model.observe(error,::showError)
        binding.model = viewModel
        binding.lifecycleOwner = this
        btnLogout.setOnClickListener {
            viewModel.logout()
        }

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
