package com.mibaldi.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mibaldi.presentation.R
import com.mibaldi.presentation.data.model.BeerView
import kotlinx.android.synthetic.main.adapter_beer_row.view.*

class BeerAdapter constructor(private val onClickAction: (BeerView) -> Unit) :
    PagedListAdapter<BeerView, BeerAdapter.MyViewHolder>(beerDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_beer_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onClickAction) }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(beerView: BeerView, onClickAction: (BeerView) -> Unit) {
            itemView.setOnClickListener { onClickAction(beerView) }
            with(beerView) {
                itemView.title.text = beerView.title
                Glide.with(itemView.context).load(image).error(R.drawable.ic_image_broken)
                    .into(itemView.image)
            }
        }
    }

    companion object {
        val beerDiffCallback = object : DiffUtil.ItemCallback<BeerView>() {
            override fun areItemsTheSame(oldItem: BeerView, newItem: BeerView): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BeerView, newItem: BeerView): Boolean {
                return oldItem == newItem
            }
        }
    }
}