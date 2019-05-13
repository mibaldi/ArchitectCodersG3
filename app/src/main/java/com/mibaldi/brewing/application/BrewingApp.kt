package com.mibaldi.brewing.application

import android.app.Application
import com.google.firebase.auth.FirebaseAuth


class BrewingApp : Application() {

    lateinit var auth: FirebaseAuth
    override fun onCreate() {
        super.onCreate()
        auth = FirebaseAuth.getInstance()
    }
}