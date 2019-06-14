package com.mibaldi.presentation.ui.map

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import androidx.lifecycle.Observer

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.mibaldi.presentation.R
import com.mibaldi.presentation.base.activities.BaseActivity
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.utils.PermissionRequester
import com.mibaldi.presentation.utils.loadUrl
import kotlinx.android.synthetic.main.activity_maps.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import android.view.animation.AnimationUtils
import com.mibaldi.presentation.ui.detail.BarDetailActivity
import com.mibaldi.presentation.utils.startActivity


class MapsActivity : BaseActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private lateinit var mMap: GoogleMap
    private val coarsePermissionRequester = PermissionRequester(this, ACCESS_COARSE_LOCATION)
    private val viewModel: MapsViewModel by currentScope.viewModel(this)
    val ZOOM_LEVEL = 13f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        hideFooter()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel.model.observe(this,Observer(::updateUI))
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        coarsePermissionRequester.request {
            if (it) {
                mMap.isMyLocationEnabled = true
            }
        }
        mMap.setOnMapClickListener {
            viewModel.onMapClick()
        }

    }

    private fun updateUI(model: MapsViewModel.UiModel){
        progress.visibility = if (model is MapsViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model){
            is  MapsViewModel.UiModel.Content -> refresh(model.bars)
            is MapsViewModel.UiModel.Footer -> showFooter(model.bar)
            is MapsViewModel.UiModel.HideFooter -> hideFooter()
            is MapsViewModel.UiModel.Navigation -> startActivity<BarDetailActivity> {
                putExtra(BarDetailActivity.BEER, model.bar)
            }
        }
    }

    private fun hideFooter() {
        if (llFooter.visibility != View.GONE) {
            val slideDown = AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.slide_down
            )
            slideDown.setAnimationListener(object : MyAnimationListener {
                override fun onAnimationEnd(animation: Animation?) {
                    llFooter.visibility = View.GONE
                    ivBar.setImageDrawable(getDrawable(R.mipmap.ic_launcher))
                    tvBar.text = ""
                }
            })
            llFooter.startAnimation(slideDown)
        }
    }



    private fun showFooter(bar: BarView) {
        val slideUp = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.slide_up
        )
        slideUp.setAnimationListener(object : MyAnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                llFooter.visibility = View.VISIBLE
                ivBar.loadUrl(bar.photo)
                tvBar.text = bar.name
            }
        })
        llFooter.setOnClickListener {
            viewModel.onFooterClicked(bar)
        }
        llFooter.startAnimation(slideUp)


    }

    private fun refresh(barViewList:List<BarView>){
        val generateMarker = generateMarker(barViewList)
        generateMarker.forEach {
            mMap.addMarker(it)
            mMap.setOnMarkerClickListener(this@MapsActivity)
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(generateMarker.last()?.position,ZOOM_LEVEL))


    }
    override fun onMarkerClick(marker: Marker): Boolean {
        marker.showInfoWindow()

        viewModel.clickOnBar(marker.position)
        return true
    }

    private fun generateMarker(barViewList: List<BarView>): List<MarkerOptions?>{
        return barViewList.map {
            val latLng = LatLng(it.address.lat, it.address.lon)
            MarkerOptions().position(latLng).title(it.name)
        }
    }

}
interface MyAnimationListener: Animation.AnimationListener {
    override fun onAnimationEnd(animation: Animation?) {}
    override fun onAnimationRepeat(animation: Animation?) {}
    override fun onAnimationStart(animation: Animation?) {}
}
