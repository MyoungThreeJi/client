package com.example.myapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.padinfo.view.*

class padInfoAdapter():RecyclerView.Adapter<padInfoAdapter.ViewHolder>(){
    var items=ArrayList<padInfo>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):padInfoAdapter.ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.padinfo,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: padInfoAdapter.ViewHolder, position: Int) {
        val item=items[position]
        holder.setItem(item)
    }

    override fun getItemCount()=items.size

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun setItem(item:padInfo){
            //itemView.img_register
            itemView.brand.text=item.brand
            itemView.product.text=item.product
            itemView.score.text=item.score.toString()
        }
    }
}