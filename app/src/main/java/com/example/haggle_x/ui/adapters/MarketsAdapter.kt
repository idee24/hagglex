package com.example.haggle_x.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.haggle_x.R

/**
 *Created by Yerimah on 10/9/2021.
 */
class MarketsAdapter: RecyclerView.Adapter<MarketsAdapter.MarketsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.markets_recycler_item, parent, false)
        return MarketsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MarketsViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 6

    inner class MarketsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}