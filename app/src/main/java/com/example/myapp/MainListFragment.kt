package com.example.myapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager


import kotlinx.android.synthetic.main.fragment_main_list.*

class MainListFragment : Fragment() {
    private lateinit var adapter: padInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root=inflater.inflate(R.layout.fragment_main_list, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val layoutManager = LinearLayoutManager(this.activity)
        //layoutManager.setReverseLayout(true)
        //layoutManager.setStackFromEnd(true)
        recyclerView1.layoutManager = layoutManager
        //recyclerView1.layoutManager = LinearLayoutManager(requireContext())

        adapter=padInfoAdapter()
        recyclerView1.adapter = adapter

        adapter.items.add(padInfo("유한컴벌리", "생리대이름", 8))
        adapter.items.add(padInfo("유한컴벌리", "유한", 7))
    }
}