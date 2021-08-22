package com.example.myapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.fragment_detail_list.*
import kotlinx.android.synthetic.main.fragment_main_list.*

class DetailListFragment : Fragment() {
    private lateinit var adapter:ListdetailAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var root =inflater.inflate(R.layout.fragment_detail_list, container, false)





        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val layoutManager = LinearLayoutManager(context)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        recy.layoutManager = layoutManager

        adapter = ListdetailAdapter()
        recy.adapter = adapter

        adapter.items.add(List_IngreItem("핵산",8,8,8))
        adapter.items.add(List_IngreItem("그로포",8,8,8))
        adapter.notifyDataSetChanged()



        gotodi.setOnClickListener {
            val fragmentManager3 = requireActivity().supportFragmentManager
            var transaction3: FragmentTransaction
            val fragmentA = Detail_IngreFragment()
            transaction3 = fragmentManager3.beginTransaction()
            val bundle = Bundle()

            bundle.putString("name1", "h")
            fragmentA.arguments=bundle
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.container,Detail_IngreFragment())
            transaction.replace(R.id.container, Detail_IngreFragment().apply { arguments = bundle })
            transaction.commit()


        }


        gotoreview.setOnClickListener {
            val fragmentManager2 = requireActivity().supportFragmentManager
            var transaction2: FragmentTransaction
            val fragmentA = ReviewFragment()
            transaction2 = fragmentManager2.beginTransaction()
            val bundle = Bundle()

            bundle.putString("name1", "h")
            fragmentA.arguments=bundle
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.container,ReviewFragment())
            transaction.replace(R.id.container, ReviewFragment().apply { arguments = bundle })
            transaction.commit()


        }



    }



}