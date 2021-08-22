package com.example.myapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_detail__ingre.*

class Detail_IngreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     var root =inflater.inflate(R.layout.fragment_detail__ingre, container, false)






        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setExpandableList()



    }

    private fun setExpandableList() {
        val parentList = mutableListOf("", "핵산", "디아블로")
        val childList = mutableListOf(
            mutableListOf("hhh"),
            mutableListOf("자식 1", "자식 2"),
            mutableListOf("자식 1", "자식 2", "자식 3")
        )
        val expandableAdapter = ExpandableListAdapter(requireContext(), parentList, childList)
        el_menu.setAdapter(expandableAdapter)
        el_menu.setOnGroupClickListener { parent, v, groupPosition, id ->
            /* todo : parent 클릭 이벤트 설정 */
            false }
        el_menu.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            /* todo : child 클릭 이벤트 설정 */
            false } }









    }
