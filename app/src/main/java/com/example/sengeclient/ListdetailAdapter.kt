package com.example.sengeclient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sengeclient.R
import kotlinx.android.synthetic.main.incredient_item.view.*

class ListdetailAdapter : RecyclerView.Adapter<ListdetailAdapter.ViewHolder>() {
var items = ArrayList<List_IngreItem>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListdetailAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.incredient_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
             holder.setItem(item)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setItem(item: List_IngreItem) {
            itemView.iname.text = item.name

        }
    }









}