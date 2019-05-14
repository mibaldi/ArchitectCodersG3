package com.mibaldi.brewing.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mibaldi.brewing.R
import com.mibaldi.brewing.base.activities.BaseActivity
import com.mibaldi.brewing.ui.main.MainActivity
import com.mibaldi.brewing.utils.observe
import com.mibaldi.brewing.utils.startActivity
import com.mibaldi.brewing.utils.withViewModel
import kotlinx.android.synthetic.main.activity_email_password.*

class EmailPasswordActivity : BaseActivity() {
    private lateinit var viewModel: EmailPasswordViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_password)
        viewModel = withViewModel({ EmailPasswordViewModel() }) {
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

    private fun updateUI(model: EmailPasswordViewModel.UiModel) {
        when (model) {
            is EmailPasswordViewModel.UiModel.Content -> {
                setupUser(model)
            }
            is EmailPasswordViewModel.UiModel.ValidateForm -> {
                when (model.field) {
                    is EmailPasswordViewModel.Field.Email -> fieldEmail.error = model.field.error
                    is EmailPasswordViewModel.Field.Password -> fieldPassword.error = model.field.error
                }
            }
            is EmailPasswordViewModel.UiModel.Navigation -> startActivity<MainActivity> {}
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