package com.example.haggle_x.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haggle_x.MainActivity
import com.example.haggle_x.R
import com.example.haggle_x.databinding.FragmentDashboardBinding
import com.example.haggle_x.ui.adapters.MarketsAdapter
import com.example.haggle_x.ui.adapters.NewsAdapter


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)
        initRecyclers()
    }


    private fun initRecyclers() {
        val layoutManager1 = LinearLayoutManager(activity)
        layoutManager1.orientation = RecyclerView.VERTICAL
        binding.marketsRecyclerView.layoutManager = layoutManager1
        binding.marketsRecyclerView.adapter = MarketsAdapter()


        val layoutManager2 = LinearLayoutManager(activity)
        layoutManager2.orientation = RecyclerView.VERTICAL
        binding.newsRecyclerView.layoutManager = layoutManager2
        binding.newsRecyclerView.adapter = NewsAdapter()
    }
}