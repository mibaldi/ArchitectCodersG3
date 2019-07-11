package com.mibaldi.presentation.ui.login

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.mibaldi.presentation.R
import com.mibaldi.presentation.base.activities.BaseActivity
import com.mibaldi.presentation.databinding.ActivityEmailPasswordBinding
import kotlinx.android.synthetic.main.activity_email_password.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class EmailPasswordActivity : BaseActivity() {

    private val viewModel: EmailPasswordViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_password)
        viewModel.error.observe(this, Observer(::showError))

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
        viewModel.onStart()
    }
}