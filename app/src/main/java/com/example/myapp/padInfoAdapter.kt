package com.example.myapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail_list.*

import kotlinx.android.synthetic.main.padinfo.view.*
import java.net.URLDecoder

class padInfoAdapter(var item:ArrayList<padlist>):RecyclerView.Adapter<padInfoAdapter.ViewHolder>(){
var items=item
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
            //items.removeAt(position)

        }
    }
    fun clearItem() = items.clear()
    override fun getItemCount()=items.size

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){




        fun setItem(item:padlist){
            itemView.lbrand.text = item.manufacturer
            itemView.lproduct.text = item.name
            itemView.score.text=item.safeScore.toString()
            //itemView.score.text = item.score.toString()
itemView.lrank.text=item.rank.toString()+"위"
           // var a= URLDecoder.decode(item.image!!.substring(ApiService.API_URL.length+1), "utf-8");
            Glide.with(itemView).load(item.image!!).into(itemView.limg)



        }
    }
}