package com.mibaldi.presentation.ui.map

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.mibaldi.data.repository.FirestoreSimpleRepository
import com.mibaldi.domain.interactors.bar.GetBarInteractor
import com.mibaldi.presentation.R
import com.mibaldi.presentation.base.activities.BaseActivity
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.databinding.ActivityMapsBindingImpl
import com.mibaldi.presentation.framework.datasources.FirestoreSimpleDataSource
import com.mibaldi.presentation.ui.common.Navigator
import com.mibaldi.presentation.utils.PermissionRequester
import com.mibaldi.presentation.utils.observe
import com.mibaldi.presentation.utils.withViewModel


class MapsActivity : BaseActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private val coarsePermissionRequester = PermissionRequester(this, ACCESS_COARSE_LOCATION)
    private lateinit var viewModel: MapsViewModel
    private lateinit var bottomSheetDialog : MapBottomDialogFragment

    val ZOOM_LEVEL = 13f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val firestoreDataSource =
            FirestoreSimpleDataSource(FirebaseFirestore.getInstance())
        val repository = FirestoreSimpleRepository(firestoreDataSource)
        val getBarInteractor = GetBarInteractor(repository)
        viewModel = withViewModel(
            {
                MapsViewModel(
                    Navigator(this),
                    getBarInteractor
                )
            }) {
            observe(bars,::refresh)
            observe(footer,::showFooter)
        }

        val binding: ActivityMapsBindingImpl =
            DataBindingUtil.setContentView(this, R.layout.activity_maps)

        binding.model = viewModel
        binding.lifecycleOwner = this

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }
    private fun showFooter(bar: BarView?) {
        bar?.let {
            showBottom(it)
            //bottomSheetDialog.show()
        } ?: bottomSheetDialog.dismiss()

    }

    private fun showBottom(bar: BarView) {
        bottomSheetDialog = MapBottomDialogFragment.newInstance(bar,viewModel)

        bottomSheetDialog.show(
            supportFragmentManager,
            "map_dialog_fragment")
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
        coarsePermissionRequester.request {
            if (it) {
                mMap.isMyLocationEnabled = true
            }
        }
        mMap.setOnMapClickListener {
            viewModel.onMapClick()
        }

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