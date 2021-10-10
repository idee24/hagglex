package com.example.haggle_x.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.haggle_x.R

/**
 *Created by Yerimah on 10/10/2021.
 */
class TopAdapter(): RecyclerView.Adapter<TopAdapter.TopViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.top_recycler_item, parent, false)
        return TopViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopViewHolder, position: Int) {
    }


    override fun getItemCount(): Int = 3


    inner class TopViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}


}