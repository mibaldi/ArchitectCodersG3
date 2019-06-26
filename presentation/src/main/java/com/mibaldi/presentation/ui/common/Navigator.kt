package com.mibaldi.presentation.ui.common

import androidx.appcompat.app.AppCompatActivity
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.ui.detail.BarDetailActivity
import com.mibaldi.presentation.ui.profile.ProfileActivity
import com.mibaldi.presentation.utils.startActivity

class Navigator(private val activity: AppCompatActivity) {
    fun goToDetail(bar: BarView) {
        activity.startActivity<BarDetailActivity> {
            putExtra(BarDetailActivity.BEER, bar)
        }
    }

    fun goToProfile() {
        activity.startActivity<ProfileActivity> {}
    }
}