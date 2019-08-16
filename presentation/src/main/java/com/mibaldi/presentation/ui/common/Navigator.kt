package com.mibaldi.presentation.ui.common

import android.content.Intent
import com.mibaldi.presentation.base.activities.BaseActivity
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.ui.detail.BarDetailActivity
import com.mibaldi.presentation.ui.login.EmailPasswordActivity
import com.mibaldi.presentation.ui.main.MainActivity
import com.mibaldi.presentation.ui.profile.ProfileActivity
import com.mibaldi.presentation.utils.startActivity

class Navigator(private val activity: BaseActivity) {
    fun goToDetail(bar: BarView) {
        activity.startActivity<BarDetailActivity> {
            putExtra(BarDetailActivity.BEER, bar)
        }
    }

    fun goToProfile() {
        activity.startActivity<ProfileActivity> {}
    }

    fun goToMain() {
        activity.startActivity<MainActivity> { activity.finish() }
    }

    fun goToLogin() {
        activity.startActivity<EmailPasswordActivity> {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.finish()
        }
    }
}