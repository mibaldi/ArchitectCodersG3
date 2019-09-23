package com.mibaldi.presentation.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mibaldi.domain.entity.MyFirebaseUser
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.data.model.BeerView
import com.mibaldi.presentation.ui.adapters.BarAdapter
import com.mibaldi.presentation.ui.adapters.BeerListAdapter
import com.mibaldi.presentation.ui.adapters.BindableAdapter


@BindingAdapter(value = ["url", "error"], requireAll = false)
fun ImageView.bindUrl(url: String?, error: Drawable?) {
    url?.let { loadUrl(it) } ?: kotlin.run { error?.let { loadResource(it) } }
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = visible?.let {
        if (visible) View.VISIBLE else View.GONE
    } ?: View.GONE
}


@BindingAdapter("user")
fun TextView.resetField(user: MyFirebaseUser?) {
    if (user == null) {
        text = ""
    }
}

@BindingAdapter("error")
fun EditText.showError(field: Field?) {
    error = field?.error
}