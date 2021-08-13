package com.example.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.detail_info.*

class ReviewActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_info)

        review.layoutManager= LinearLayoutManager(this)
        val adapter=reviewInfoAdapter()
        adapter.items.add(reviewInfo("생리대1","2021-08-13",4.97.toFloat(),4.0.toFloat(),3.0.toFloat(),2.0.toFloat(),"착용감이 좋아요"))
    }
}