package com.mibaldi.presentation.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.mibaldi.data.repository.LoginRepositoryImpl
import com.mibaldi.domain.entity.MyFirebaseUser
import com.mibaldi.domain.interactors.account.RemoveAccountInteractor
import com.mibaldi.domain.interactors.user.GetCurrentUserInteractor
import com.mibaldi.domain.interactors.login.SignOutInteractor
import com.mibaldi.presentation.R
import com.mibaldi.presentation.base.activities.BaseActivity
import com.mibaldi.presentation.framework.datasources.LoginDataSourceImpl
import com.mibaldi.presentation.ui.login.EmailPasswordActivity
import com.mibaldi.presentation.utils.loadUrl
import com.mibaldi.presentation.utils.observe
import com.mibaldi.presentation.utils.startActivity
import com.mibaldi.presentation.utils.withViewModel
import kotlinx.android.synthetic.main.activity_main.progress
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var getCurrentUserInteractor: GetCurrentUserInteractor
    private lateinit var signOutInteractor: SignOutInteractor
    private lateinit var removeAccountInteractor: RemoveAccountInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        profileToolbar.title = ""
        setSupportActionBar(profileToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val loginRepository = LoginRepositoryImpl(LoginDataSourceImpl)
        getCurrentUserInteractor = GetCurrentUserInteractor(loginRepository)
        signOutInteractor = SignOutInteractor(loginRepository)
        removeAccountInteractor = RemoveAccountInteractor(loginRepository)
        viewModel =
            withViewModel({ ProfileViewModel(getCurrentUserInteractor, signOutInteractor, removeAccountInteractor) }) {
                observe(model, ::updateUI)
            }
        btnLogout.setOnClickListener {
            viewModel.logout()
        }
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

    private fun updateUI(model: ProfileViewModel.UiModel) {
        progress.visibility = if (model is ProfileViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is ProfileViewModel.UiModel.Content -> {
                setupUser(model.myFirebaseUser)
            }

            is ProfileViewModel.UiModel.Navigation -> startActivity<EmailPasswordActivity> {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                finish()
            }
            is ProfileViewModel.UiModel.Error -> showError(model.errorString)
        }

    }

    private fun showError(errorString: String) {
        Snackbar.make(coordinatorLayout, errorString, Snackbar.LENGTH_LONG).show()
    }

    private fun setupUser(myFirebaseUser: MyFirebaseUser) {
        myFirebaseUser.apply {
            supportActionBar?.title = name
            profileImage.loadUrl(photoUrl)
            profileSummary.text = "Email :$email"
        }

    }
}
