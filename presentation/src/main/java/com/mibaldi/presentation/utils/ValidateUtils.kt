package com.mibaldi.presentation.utils

import android.text.TextUtils
import android.util.Patterns

sealed class Field ( val error: String?) {
    class Email(error2:String?) : Field(error2)
    class Password(error2:String?): Field(error2)
}


fun validatePassword(password: String, valid: Boolean,myFunction:(Field)-> Unit): Boolean {
    var valid1 = valid
    when {
        TextUtils.isEmpty(password) -> {
            myFunction(Field.Password("Required."))
            valid1 = false
        }
        password.length < 6 -> {
            myFunction(Field.Password("The password should be at least 6 character."))
            valid1 = false
        }
        else -> {
            myFunction(Field.Password(null))
        }
    }
    return valid1
}

fun validateEmail(email: String, valid: Boolean,myFunction:(Field)-> Unit): Boolean {
    var valid1 = valid
    when {
        TextUtils.isEmpty(email) -> {
            myFunction(Field.Email("Required"))
            valid1 = false
        }
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
            myFunction(Field.Email("The email address is badly formatted."))
            valid1 = false
        }
        else -> myFunction(Field.Email(null))
    }
    return valid1
}