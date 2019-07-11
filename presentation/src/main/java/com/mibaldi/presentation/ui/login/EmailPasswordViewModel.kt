package com.mibaldi.presentation.ui.login

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mibaldi.domain.interactors.account.CreateAccountInteractor
import com.mibaldi.domain.interactors.login.SignInInteractor
import com.mibaldi.domain.interactors.login.SignOutInteractor
import com.mibaldi.domain.interactors.user.GetCurrentUserInteractor
import com.mibaldi.presentation.ui.common.Navigator
import com.mibaldi.presentation.ui.common.Scope
import com.mibaldi.presentation.utils.Field
import com.mibaldi.presentation.utils.validateEmail
import com.mibaldi.presentation.utils.validatePassword
import kotlinx.coroutines.launch

class EmailPasswordViewModel(
    private val navigator: Navigator,
    private val signInInteractor: SignInInteractor,
    private val getCurrentUserInteractor: GetCurrentUserInteractor,
    private val createAccountInteractor: CreateAccountInteractor,
    private val signOutInteractor: SignOutInteractor
) : ViewModel(), Scope by Scope.Impl() {

    var emailField: ObservableField<String> = ObservableField("")
    var passwordField: ObservableField<String> = ObservableField("")
    var signedInButtons: ObservableBoolean = ObservableBoolean(false)

    val user = MutableLiveData<Boolean>()

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error
    private val _validateFormEmail = MutableLiveData<Field>()
    val validateFormEmail: LiveData<Field>
        get() = _validateFormEmail

    private val _validateFormPassword = MutableLiveData<Field>()
    val validateFormPassword: LiveData<Field>
        get() = _validateFormPassword


    init {
        initScope()

    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }


    fun signOut() {
        launch {
            signOutInteractor.signOut()
            signedInButtons.set(false)
            emailField.set("")
        }
    }

    fun signIn() {
        val email = emailField.get() ?: ""
        val password = passwordField.get() ?: ""
        Log.d("EmailPassword", "signIn:$email")
        if (!validateForm(email, password)) {
            return
        }
        launch {
            _dataLoading.value = true
            val signIn = signInInteractor.signIn(email, password)
            signIn.either(::handleFailure, ::handleSuccess)
            _dataLoading.value = false

        }
    }

    fun createAccount() {
        val email = emailField.get() ?: ""
        val password = passwordField.get() ?: ""
        if (!validateForm(email, password)) {
            return
        }
        launch {
            _dataLoading.value = true
            val createAccount = createAccountInteractor.createAccount(email, password)
            createAccount.either(::handleFailure, ::handleSuccess)
            _dataLoading.value = false

        }
    }

    private fun handleSuccess(signInResult: Boolean) {
        navigator.goToMain()
    }

    private fun handleFailure(error: String) {
        _error.value = error
    }

    fun onStart() {
        launch {
            val currentUser = getCurrentUserInteractor.currentUser()
            emailField.set(currentUser?.email)
            signedInButtons.set(currentUser != null)
            if (currentUser != null) {
                //navigator.goToMain()
            }
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        var valid = true
        valid = validateEmail(email, valid) {
            _validateFormEmail.value = it
        }
        valid = validatePassword(password, valid) {
            _validateFormPassword.value = it
        }
        return valid
    }

    companion object {
        val TAG = "EmailPassword"
    }

}