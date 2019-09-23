package com.mibaldi.presentation.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mibaldi.presentation.R
import com.mibaldi.presentation.data.model.BeerView
import com.mibaldi.presentation.utils.basicDiffUtil
import com.mibaldi.presentation.utils.inflate
import kotlinx.android.synthetic.main.item_list_beer.view.*

class BeerListAdapter :
    RecyclerView.Adapter<BeerListAdapter.ViewHolder>(), BindableAdapter<List<BeerView>> {

    override var data: List<BeerView> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old,
                            new ->
            (old.id == new.id && old.rating == new.rating)
        }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_list_beer)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val beer = data[position]
        holder.bind(beer)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(beerView: BeerView) = with(itemView) {
            nameText.text = beerView.title
            ratingBar.rating = beerView.rating
            Glide.with(context).load(beerView.image).error(R.drawable.ic_image_broken)
                .into(beerImage)
        }
    }

}

@BindingAdapter("data")
fun RecyclerView.setRecyclerViewProperties(items: List<BeerView>?) {
    if (adapter is BeerListAdapter) {
        (adapter as BeerListAdapter).data = items ?: emptyList()
    }
}