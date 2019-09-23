package com.mibaldi.presentation.ui.detail.beer

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mibaldi.presentation.R
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.data.model.BeerView
import com.mibaldi.presentation.databinding.FragmentAddBeerBottomSheetBinding
import com.mibaldi.presentation.ui.detail.BarDetailViewModel
import com.mibaldi.presentation.ui.detail.beer.BeerListActivity.Companion.BEER_SELECTED
import com.mibaldi.presentation.utils.EventObserver
import com.mibaldi.presentation.utils.startActivityForResult
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AddBeerBottomDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: AddBeerViewModel by currentScope.viewModel(this) {
        parametersOf(arguments?.getParcelable(BAR_SELECTED))
    }

    private val sharedViewModel: BarDetailViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAddBeerBottomSheetBinding>(
            inflater,
            R.layout.fragment_add_beer_bottom_sheet,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.rating.observe(this, Observer { if (it > 0) expand() })
        viewModel.navigateToBeerList.observe(this, EventObserver {
            startActivityForResult<BeerListActivity>(
                REQUEST_CODE_BEER
            ) {}
        })
        viewModel.close.observe(this, EventObserver { dismiss() })
        viewModel.accept.observe(this, EventObserver {
            sharedViewModel.refresh(it)
            dismiss()
        })
        return binding.root
    }

    companion object {
        const val REQUEST_CODE_BEER = 101
        const val BAR_SELECTED = "AddBeerBottomDialogFragment:bar"
        fun newInstance(bar: BarView): AddBeerBottomDialogFragment {
            return AddBeerBottomDialogFragment().apply {
                val bundle = Bundle()
                bundle.putParcelable(BAR_SELECTED, bar)
                arguments = bundle
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_BEER && resultCode == RESULT_OK) {
            data?.getParcelableExtra<BeerView>(BEER_SELECTED)?.run(viewModel::setData)
        }
    }

    private fun expand() {
        val bottomSheetDialog = dialog as BottomSheetDialog
        val bottomSheet =
            bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
    }
}