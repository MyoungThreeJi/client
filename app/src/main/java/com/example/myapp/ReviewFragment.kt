package com.example.myapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_detail_list.*
import kotlinx.android.synthetic.main.fragment_main_list.*
import kotlinx.android.synthetic.main.fragment_review.*
import kotlinx.android.synthetic.main.review_dialog.*
import kotlinx.android.synthetic.main.review_dialog.rat1
import kotlinx.android.synthetic.main.review_dialog.rat2
import kotlinx.android.synthetic.main.review_dialog.rat3
import kotlinx.android.synthetic.main.review_dialog.rat4
import kotlinx.android.synthetic.main.reviewinfo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReviewFragment : Fragment() {
    private lateinit var adapter: reviewInfoAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        reviewre.layoutManager = layoutManager

        adapter = reviewInfoAdapter()
        reviewre.adapter = adapter

        gotoreview.setOnClickListener {
            var retrofit = Retrofit.Builder().baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
            var apiService = retrofit.create(ApiService::class.java)
            var reviews = apiService.get_review("json")

            reviews.enqueue(object : Callback<List<reviewInfo>> {
                override fun onResponse(
                    call: Call<List<reviewInfo>>,
                    response: Response<List<reviewInfo>>
                ) {
                    if(response.isSuccessful){
                        Log.d("get_review","성공")
                        var reviewList=response.body()!!
                        rat1.rating=reviewList.get(1).star1!!.toFloat()
                        rat2.rating=reviewList.get(1).star2!!.toFloat()
                        rat3.rating=reviewList.get(1).star3!!.toFloat()
                        rat4.rating=reviewList.get(1).star4!!.toFloat()
                        date.text=reviewList.get(1).created
                        review_content.text=reviewList.get(1).content
                    }
                }

                override fun onFailure(call: Call<List<reviewInfo>>, t: Throwable) {
                    Log.d("get_review","실패")
                }
            })
        }
        post_review.setOnClickListener{
            var retrofit = Retrofit.Builder().baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
            var apiService = retrofit.create(ApiService::class.java)

            //var reviewData=p_reviewInfo()
            //var review = apiService.reg_review(reviewData)

            /*review.enqueue(object : Callback<List<reviewInfo>> {
                override fun onResponse(
                    call: Call<List<reviewInfo>>,
                    response: Response<List<reviewInfo>>
                ) {

                }

                override fun onFailure(call: Call<List<reviewInfo>>, t: Throwable) {

                }
            })
            */
        }

    }


}