package com.mibaldi.presentation.ui.map

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mibaldi.presentation.R
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.utils.loadUrl

internal class MapBottomDialogFragment(private val bar: BarView,
                                       private val viewModel: MapsViewModel) : BottomSheetDialogFragment() {

    @Nullable
    override fun onCreateView(inflater: LayoutInflater,
                              @Nullable container: ViewGroup?,
                              @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_map_footer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bar.let {
            it.photo?.let {photo ->
                view.findViewById<ImageView>(R.id.ivBar)?.loadUrl(photo)
            }
            view.findViewById<TextView>(R.id.tvBar)?.text = it.name
            view.findViewById<LinearLayout>(R.id.llFooter)?.setOnClickListener { view ->
                viewModel.onFooterClicked(it)
            }
        }

    }

    companion object {

        fun newInstance(bar: BarView,viewModel:MapsViewModel): MapBottomDialogFragment {
            return MapBottomDialogFragment(bar,viewModel)
        }
    }
}