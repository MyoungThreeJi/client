package com.example.myapp

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.ResourceLoader
import kotlinx.android.synthetic.main.reviewinfo.view.*
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.lang.NullPointerException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLConnection
import kotlin.concurrent.thread


class reviewInfoAdapter(var items: List<reviewInfo>) : RecyclerView.Adapter<reviewInfoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.reviewinfo, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setItem(item: reviewInfo) {
            Glide.with(itemView).load(item.userImage).into(itemView.profile)
            itemView.user_id.text = item.userName
            itemView.date.text = item.created
            itemView.rat1.rating = item.star1!!
            itemView.rat2.rating = item.star2!!
            itemView.rat3.rating = item.star3!!
            itemView.rat4.rating = item.star4!!
            itemView.review_content.text = item.content
        }
    }
}