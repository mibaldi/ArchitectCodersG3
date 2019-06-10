package com.mibaldi.presentation.ui.login

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.mibaldi.data.repository.LoginRepositoryImpl
import com.mibaldi.domain.interactors.account.CreateAccountInteractor
import com.mibaldi.domain.interactors.user.GetCurrentUserInteractor
import com.mibaldi.domain.interactors.login.SignInInteractor
import com.mibaldi.domain.interactors.login.SignOutInteractor
import com.mibaldi.presentation.R
import com.mibaldi.presentation.base.activities.BaseActivity
import com.mibaldi.presentation.framework.datasources.LoginDataSourceImpl
import com.mibaldi.presentation.ui.main.MainActivity
import com.mibaldi.presentation.utils.Field
import com.mibaldi.presentation.utils.observe
import com.mibaldi.presentation.utils.startActivity
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
        setContentView(R.layout.activity_email_password)
        initInteractors()
        viewModel = withViewModel(
            {
                EmailPasswordViewModel(
                    signInInteractor,
                    getCurrentUserInteractor,
                    createAccountInteractor,
                    signOutInteractor
                )
            }) {
            observe(model, ::updateUI)
        }
        emailSignInButton.setOnClickListener {
            viewModel::signIn.invoke(fieldEmail.text.toString(), fieldPassword.text.toString())
        }
        emailCreateAccountButton.setOnClickListener {
            viewModel::createAccount.invoke(fieldEmail.text.toString(), fieldPassword.text.toString())
        }
        signOutButton.setOnClickListener {
            viewModel::signOut.invoke()
        }
    }

    private fun initInteractors() {
        val loginRepository = LoginRepositoryImpl(LoginDataSourceImpl)
        getCurrentUserInteractor = GetCurrentUserInteractor(loginRepository)
        createAccountInteractor = CreateAccountInteractor(loginRepository)
        signInInteractor = SignInInteractor(loginRepository)
        signOutInteractor = SignOutInteractor(loginRepository)
    }

    private fun updateUI(model: EmailPasswordViewModel.UiModel) {
        when (model) {
            is EmailPasswordViewModel.UiModel.Content -> {
                setupUser(model)
            }
            is EmailPasswordViewModel.UiModel.ValidateForm -> {
                when (model.field) {
                    is Field.Email -> fieldEmail.error = model.field.error
                    is Field.Password -> fieldPassword.error = model.field.error
                }
            }
            is EmailPasswordViewModel.UiModel.Navigation -> startActivity<MainActivity> { finish() }
            is EmailPasswordViewModel.UiModel.Loading -> {
                if (model.show) showProgressDialog() else hideProgressDialog()
            }
            is EmailPasswordViewModel.UiModel.Error -> {
                Snackbar.make(main_layout, model.errorString, Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    private fun setupUser(model: EmailPasswordViewModel.UiModel.Content) {
        model.user.apply {
            if (this != null) {
                fieldEmail.setText(email)
                fieldPassword.setText("")
                signedInButtons.visibility = View.VISIBLE
            } else {
                fieldEmail.setText("")
                fieldPassword.setText("")
                signedInButtons.visibility = View.GONE
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        viewModel::onStart.invoke()
    }
}