package com.mibaldi.brewing.ui.login

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.mibaldi.brewing.interactors.CreateAccountInteractor.CreateAccountInteractor
import com.mibaldi.brewing.interactors.GetCurrentUserInteractor.GetCurrentUserInteractor
import com.mibaldi.brewing.interactors.SignInInteractor.SignInInteractor
import com.mibaldi.brewing.interactors.SignOutInteractor.SignOutInteractor
import com.mibaldi.brewing.ui.common.Scope
import kotlinx.coroutines.launch

class EmailPasswordViewModel(private val signInInteractor: SignInInteractor,
                             private val getCurrentUserInteractor: GetCurrentUserInteractor,
                             private val createAccountInteractor: CreateAccountInteractor,
                             private val signOutInteractor: SignOutInteractor) : ViewModel() , Scope by Scope.Impl {

    sealed class UiModel {
        class Content(val user:FirebaseUser?) : UiModel()
        object Navigation : UiModel()
        class ValidateForm(val field:Field): UiModel()
        class Loading(val show:Boolean) : UiModel()
        class Error(val errorString: String) : UiModel()
    }
    sealed class Field {
        class Email(val error:String?) : Field()
        class Password(val error:String?): Field()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) _model.value = UiModel.Content(getCurrentUserInteractor.currentUser())
            return _model
        }

    init {
        initScope()
    }
    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }


    fun signOut() {
        signOutInteractor.signOut()
        _model.value = UiModel.Content(null)
    }

    fun signIn(email: String,password: String){
        Log.d("EmailPassword", "signIn:$email")
        if (!validateForm(email,password)) {
            return
        }
        launch {
            _model.value = UiModel.Loading(true)
            val signIn = signInInteractor.signIn(email, password)
            signIn.either(::handleFailure,::handleSuccess)
            _model.value = UiModel.Loading(false)
        }
    }
    fun createAccount(email: String,password: String) {
        if (!validateForm(email,password)) {
            return
        }
        launch {
            _model.value = UiModel.Loading(true)
            val createAccount = createAccountInteractor.createAccount(email, password)
            createAccount.either(::handleFailure,::handleSuccess)
            _model.value = UiModel.Loading(false)
        }
    }

    private fun handleSuccess(signInResult: Boolean) {
        _model.value = UiModel.Navigation
    }

    private fun handleFailure(error: String) {
        _model.value = UiModel.Error(error)
        _model.value = UiModel.Content(null)
    }

    fun onStart(){
        _model.value = UiModel.Content(getCurrentUserInteractor.currentUser())
    }

    private fun validateForm(email: String,password: String): Boolean {
        var valid = true
        valid = validateEmail(email, valid)
        valid = validatePassword(password, valid)

        return valid
    }

    private fun validatePassword(password: String, valid: Boolean): Boolean {
        var valid1 = valid
        when {
            TextUtils.isEmpty(password) -> {
                _model.value = UiModel.ValidateForm(Field.Password("Required."))
                valid1 = false
            }
            password.length < 6 -> {
                _model.value = UiModel.ValidateForm(Field.Password("The password should be at least 6 character."))
                valid1 = false
            }
            else -> _model.value = UiModel.ValidateForm(Field.Password(null))
        }
        return valid1
    }

    private fun validateEmail(email: String, valid: Boolean): Boolean {
        var valid1 = valid
        when {
            TextUtils.isEmpty(email) -> {
                _model.value = UiModel.ValidateForm(Field.Email("Required"))
                valid1 = false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _model.value = UiModel.ValidateForm(Field.Email("The email address is badly formatted."))
                valid1 = false
            }
            else -> _model.value = UiModel.ValidateForm(Field.Email(null))
        }
        return valid1
    }

    companion object {
        val TAG = "EmailPassword"
    }

}