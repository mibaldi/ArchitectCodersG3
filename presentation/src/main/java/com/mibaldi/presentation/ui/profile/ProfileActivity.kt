package com.mibaldi.presentation.ui.profile

import android.os.Bundle
import android.view.View
import com.mibaldi.data.repository.LoginRepositoryImpl
import com.mibaldi.domain.entity.MyFirebaseUser
import com.mibaldi.domain.interactors.getCurrentUserInteractor.GetCurrentUserInteractor
import com.mibaldi.domain.interactors.getCurrentUserInteractor.GetCurrentUserInteractorImpl
import com.mibaldi.domain.interactors.signOutInteractor.SignOutInteractor
import com.mibaldi.presentation.R
import com.mibaldi.presentation.base.activities.BaseActivity
import com.mibaldi.presentation.datasources.LoginDataSourceImpl
import com.mibaldi.presentation.ui.login.EmailPasswordActivity
import com.mibaldi.presentation.utils.observe
import com.mibaldi.presentation.utils.startActivity
import com.mibaldi.presentation.utils.withViewModel
import kotlinx.android.synthetic.main.activity_main.*

class ProfileActivity : BaseActivity() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var getCurrentUserInteractor: GetCurrentUserInteractor
    private lateinit var signOutInteractor: SignOutInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val loginRepository = LoginRepositoryImpl(LoginDataSourceImpl)
        getCurrentUserInteractor = GetCurrentUserInteractorImpl(loginRepository)
        viewModel = withViewModel({ ProfileViewModel(getCurrentUserInteractor) }) {
            observe(model, ::updateUI)
        }
    }
    private fun updateUI(model: ProfileViewModel.UiModel) {
        progress.visibility = if (model is ProfileViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is ProfileViewModel.UiModel.Content -> {
                setupUser(model.myFirebaseUser)
            }

            is ProfileViewModel.UiModel.Navigation -> startActivity<EmailPasswordActivity> {}
        }

    }

    private fun setupUser(myFirebaseUser: MyFirebaseUser?) {

    }
}
