package com.example.myapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.padinfo.view.*

class padInfoAdapter(var items:List<padlist>):RecyclerView.Adapter<padInfoAdapter.ViewHolder>(){


    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener

    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):padInfoAdapter.ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.padinfo,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: padInfoAdapter.ViewHolder, position: Int) {
        val item=items[position]
        holder.setItem(item)

        holder.itemView.setOnClickListener {
            itemClickListner.onClick(it, position)
            //MainListFragment.todetail1(position)


        }
    }

    override fun getItemCount()=items.size

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){




        fun setItem(item:padlist){
            itemView.lbrand.text = item.manufacturer
            itemView.lproduct.text = item.name
            //itemView.score.text = item.score.toString()




        }
    }
}