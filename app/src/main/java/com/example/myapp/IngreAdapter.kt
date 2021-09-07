package com.example.myapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.menu_parent.view.*

import kotlinx.android.synthetic.main.padinfo.view.*

class IngreAdapter(var items:MutableList<parent>):RecyclerView.Adapter<IngreAdapter.ViewHolder>(){


    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener

    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):IngreAdapter.ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.menu_parent,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngreAdapter.ViewHolder, position: Int) {
        val item=items[position]
        holder.setItem(item)

        holder.itemView.setOnClickListener {
            itemClickListner.onClick(it, position)
            //MainListFragment.todetail1(position)


        }
    }

    override fun getItemCount()=items.size

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun setItem(item:parent){
            itemView.tv_list_title.text = item.name
            itemView.average.text = item.average.toString()
            itemView.detection.text = item.detection.toString()
            //itemView.score.text = item.score.toString()
            if(item.image.toString().equals("고")){
            itemView.dimage.setImageResource(R.drawable.high)}
                        else{
                itemView.dimage.setImageResource(R.drawable.low)
                        }
        }
    }
}