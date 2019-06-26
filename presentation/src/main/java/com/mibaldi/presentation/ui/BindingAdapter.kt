package com.mibaldi.presentation.ui

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mibaldi.presentation.ui.adapters.BindableAdapter
import com.mibaldi.presentation.utils.loadUrl


@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    if (url != null) loadUrl(url)
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = visible?.let {
        if (visible) View.VISIBLE else View.GONE
    } ?: View.GONE
}

@BindingAdapter("data")
fun <T> RecyclerView.setRecyclerViewProperties(items: T?) {
    if (adapter is BindableAdapter<*>) {
        items?.let { data -> (adapter as BindableAdapter<T>).data = data }
    }
}