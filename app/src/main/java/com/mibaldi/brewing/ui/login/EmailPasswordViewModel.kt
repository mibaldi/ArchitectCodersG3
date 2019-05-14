package com.mibaldi.brewing.ui.login

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class EmailPasswordViewModel() : ViewModel() {

    val auth = FirebaseAuth.getInstance()
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
            if (_model.value == null) _model.value = UiModel.Content(auth.currentUser)
            return _model
        }
    fun signOut() {
        auth.signOut()
        _model.value = UiModel.Content(null)
    }
    fun signIn(email: String,password: String){
        Log.d("EmailPassword", "signIn:$email")
        if (!validateForm(email,password)) {
            return
        }
        _model.value = UiModel.Loading(true)

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    _model.value = UiModel.Navigation
                } else {
                    Log.w(TAG, "signInWithEmail:failure", it.exception)
                    _model.value = UiModel.Error("Authentication failed.")
                    _model.value = UiModel.Content(null)
                }

                if (!it.isSuccessful) {
                    _model.value = UiModel.Error("Authentication failed.")
                }
                _model.value = UiModel.Loading(false)
            }

    }
    fun onStart(){
        val user = auth.currentUser
        _model.value = UiModel.Content(user)
    }

    fun createAccount(email: String,password: String) {
        if (!validateForm(email,password)) {
            return
        }
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "createUserWithEmail:success")
                _model.value = UiModel.Navigation
            } else {
                Log.w(TAG, "createUserWithEmail:failure", it.exception)
                _model.value = UiModel.Error("Authentication failed.")
                _model.value = UiModel.Content(null)
            }
        }
    }

    private fun validateForm(email: String,password: String): Boolean {
        var valid = true
        if (TextUtils.isEmpty(email)) {
            _model.value = UiModel.ValidateForm(Field.Email("Required"))
            valid = false
        } else {
            _model.value = UiModel.ValidateForm(Field.Email(null))
        }
        if (TextUtils.isEmpty(password)) {
            _model.value = UiModel.ValidateForm(Field.Password("Required."))
            valid = false
        } else {
            _model.value = UiModel.ValidateForm(Field.Password(null))
        }

        return valid
    }

    companion object {
        val TAG = "EmailPassword"
    }

}