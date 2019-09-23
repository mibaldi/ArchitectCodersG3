package com.mibaldi.presentation.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mibaldi.presentation.R
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.utils.basicDiffUtil
import com.mibaldi.presentation.utils.inflate
import kotlinx.android.synthetic.main.view_beer.view.*

class BarAdapter(private val listener: (BarView) -> Unit) :
    RecyclerView.Adapter<BarAdapter.ViewHolder>(), BindableAdapter<List<BarView>> {

    override var data: List<BarView> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_beer)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bars = data[position]
        holder.bind(bars)
        holder.itemView.setOnClickListener { listener(bars) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(bars: BarView) = with(itemView) {
            barTitle.text = bars.name
            Glide.with(context).load(bars.photo).error(R.drawable.ic_image_broken).into(image)
        }
    }
}


@BindingAdapter("data")
fun RecyclerView.setRecyclerViewProperties(items: List<BarView>?) {
    if (adapter is BarAdapter) {
        (adapter as BarAdapter).data = items ?: emptyList()
    }
}