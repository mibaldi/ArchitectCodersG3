package com.mibaldi.presentation.ui.login

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
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
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class EmailPasswordActivity : BaseActivity() {

    private val viewModel: EmailPasswordViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_password)
        viewModel.model.observe(this, Observer(::updateUI))
        emailSignInButton.setOnClickListener {
            viewModel::signIn.invoke(fieldEmail.text.toString(), fieldPassword.text.toString())
        }
        val binding: ActivityEmailPasswordBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_email_password)

        binding.model = viewModel
        binding.lifecycleOwner = this
    }

    private fun showError(error: String) {
        Snackbar.make(main_layout, error, Snackbar.LENGTH_SHORT).show()
    }

    public override fun onStart() {
        super.onStart()
        viewModel::onStart.invoke()
    }
}