package com.mibaldi.presentation.ui.login

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.mibaldi.data.repository.LoginRepositoryImpl
import com.mibaldi.domain.entity.MyFirebaseUser
import com.mibaldi.domain.interactors.account.CreateAccountInteractor
import com.mibaldi.domain.interactors.user.GetCurrentUserInteractor
import com.mibaldi.domain.interactors.login.SignInInteractor
import com.mibaldi.domain.interactors.login.SignOutInteractor
import com.mibaldi.presentation.R
import com.mibaldi.presentation.base.activities.BaseActivity
import com.mibaldi.presentation.databinding.ActivityEmailPasswordBinding
import com.mibaldi.presentation.framework.datasources.LoginDataSourceImpl
import com.mibaldi.presentation.ui.common.Navigator
import com.mibaldi.presentation.utils.observe
import com.mibaldi.presentation.utils.withViewModel
import kotlinx.android.synthetic.main.activity_email_password.*

class EmailPasswordActivity : BaseActivity() {
    private lateinit var viewModel: EmailPasswordViewModel
    private lateinit var createAccountInteractor: CreateAccountInteractor
    private lateinit var getCurrentUserInteractor: GetCurrentUserInteractor
    private lateinit var signInInteractor: SignInInteractor
    private lateinit var signOutInteractor: SignOutInteractor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInteractors()
        viewModel = withViewModel(
            {
                EmailPasswordViewModel(
                    Navigator(this),
                    signInInteractor,
                    getCurrentUserInteractor,
                    createAccountInteractor,
                    signOutInteractor
                )
            }) {
            observe(error,::showError)
        }
        val binding: ActivityEmailPasswordBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_email_password)

        binding.model = viewModel
        binding.lifecycleOwner = this
    }

    private fun showError(error: String) {
        Snackbar.make(main_layout, error, Snackbar.LENGTH_SHORT).show()
    }

    private fun initInteractors() {
        val loginRepository = LoginRepositoryImpl(LoginDataSourceImpl)
        getCurrentUserInteractor = GetCurrentUserInteractor(loginRepository)
        createAccountInteractor = CreateAccountInteractor(loginRepository)
        signInInteractor = SignInInteractor(loginRepository)
        signOutInteractor = SignOutInteractor(loginRepository)
    }


    public override fun onStart() {
        super.onStart()
        viewModel::onStart.invoke()
    }
}