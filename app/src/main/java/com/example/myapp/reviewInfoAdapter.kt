package com.example.myapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.reviewinfo.view.*


class reviewInfoAdapter(var items:List<reviewInfo>) : RecyclerView.Adapter<reviewInfoAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.reviewinfo,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=items[position]
        holder.setItem(item)
    }

    override fun getItemCount()=items.size

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun setItem(item:reviewInfo){
            itemView.profile.setImageResource(R.drawable.heart)
            itemView.user_id.text="익명의 리뷰어"
            itemView.date.text=item.created
            itemView.rat1.rating=item.star1!!
            itemView.rat2.rating=item.star2!!
            itemView.rat3.rating=item.star3!!
            itemView.rat4.rating=item.star4!!
            itemView.review_content.text=item.content
        }
    }
}