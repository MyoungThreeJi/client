package com.example.sengeclient

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


        val layoutManager = LinearLayoutManager(activity)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        recyclerView1.layoutManager = layoutManager

        adapter = padInfoAdapter()
        recyclerView1.adapter = adapter

        adapter.items.add(padInfo(8, "유한컴벌리", "생리대이름", 7))
        adapter.items.add(padInfo(9, "유한컴벌리", "유한", 7))
        adapter.notifyDataSetChanged()

        adapter.setItemClickListener(object : padInfoAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val fragmentManager3 = activity!!.supportFragmentManager
                 var transaction3: FragmentTransaction
                val fragmentA = DetailListFragment()
                transaction3 = fragmentManager3.beginTransaction()
                val bundle = Bundle()

                bundle.putString("name1", "h")
                fragmentA.arguments=bundle
                val transaction = activity!!.supportFragmentManager.beginTransaction()
                transaction.add(R.id.container,DetailListFragment())
                transaction.replace(R.id.container, DetailListFragment().apply { arguments = bundle })
                transaction.commit()



            }


        })
    }
}