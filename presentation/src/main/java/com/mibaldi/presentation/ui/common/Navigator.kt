package com.mibaldi.presentation.ui.common

import android.content.Intent
import androidx.fragment.app.Fragment
import com.mibaldi.presentation.base.activities.BaseActivity
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.ui.detail.BarDetailActivity
import com.mibaldi.presentation.ui.detail.beer.AddBeerBottomDialogFragment
import com.mibaldi.presentation.ui.detail.beer.BeerListActivity
import com.mibaldi.presentation.ui.login.EmailPasswordActivity
import com.mibaldi.presentation.ui.main.MainActivity
import com.mibaldi.presentation.ui.map.MapsActivity
import com.mibaldi.presentation.ui.profile.ProfileActivity
import com.mibaldi.presentation.utils.startActivity
import com.mibaldi.presentation.utils.startActivityForResult

class Navigator(private val activity: BaseActivity) {
    fun goToDetail(barId: String) {
        activity.startActivity<BarDetailActivity> {
            putExtra(BarDetailActivity.BEER_ID, barId)
        }
    }

    fun goToProfile() {
        activity.startActivity<ProfileActivity> {}
    }

    fun showAddBeer(bar: BarView) {
        val addPhotoBottomDialogFragment = AddBeerBottomDialogFragment.newInstance(bar)

        addPhotoBottomDialogFragment.show(
            activity.supportFragmentManager,
            "add_beer_dialog_fragment"
        )
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

    fun goToMap() {
        activity.startActivity<MapsActivity> {}
    }
}