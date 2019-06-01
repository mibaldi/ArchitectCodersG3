package com.mibaldi.presentation.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mibaldi.domain.entity.MyFirebaseUser
import com.mibaldi.domain.interactors.createAccountInteractor.CreateAccountInteractor
import com.mibaldi.domain.interactors.getCurrentUserInteractor.GetCurrentUserInteractor
import com.mibaldi.domain.interactors.signInInteractor.SignInInteractor
import com.mibaldi.domain.interactors.signOutInteractor.SignOutInteractor
import com.mibaldi.presentation.utils.Field
import com.mibaldi.presentation.ui.common.Scope
import com.mibaldi.presentation.utils.validateEmail
import com.mibaldi.presentation.utils.validatePassword
import kotlinx.coroutines.launch

class EmailPasswordViewModel(private val signInInteractor: SignInInteractor,
                             private val getCurrentUserInteractor: GetCurrentUserInteractor,
                             private val createAccountInteractor: CreateAccountInteractor,
                             private val signOutInteractor: SignOutInteractor
) : ViewModel() , Scope by Scope.Impl() {

    sealed class UiModel {
        class Content(val user: MyFirebaseUser?) : UiModel()
        object Navigation : UiModel()
        class ValidateForm(val field: Field): UiModel()
        class Loading(val show:Boolean) : UiModel()
        class Error(val errorString: String) : UiModel()
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
        if (getCurrentUserInteractor.currentUser() != null){
            _model.value = UiModel.Navigation
        } else {
            _model.value = UiModel.Content(null)
        }

    }

    private fun validateForm(email: String,password: String): Boolean {
        var valid = true
        valid = validateEmail(email, valid) {
            _model.value = UiModel.ValidateForm(it)
        }
        valid = validatePassword(password, valid) {
            _model.value = UiModel.ValidateForm(it)
        }
        return valid
    }

    companion object {
        val TAG = "EmailPassword"
    }

}