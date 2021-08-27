package com.example.myapp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
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

class ReviewFragment : Fragment(){
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

        var retrofit = Retrofit.Builder().baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        var apiService = retrofit.create(ApiService::class.java)
        var reviews = apiService.get_review("json")

        reviews.enqueue(object : Callback<List<reviewInfo>> {
            override fun onResponse(
                    call: Call<List<reviewInfo>>,
                    response: Response<List<reviewInfo>>
            ) {
                if (response.isSuccessful) {
                    Log.d("get_review", "성공-${response.body().toString()}")

                    var reviewList=response.body()!!

                    adapter = reviewInfoAdapter(reviewList)
                    num_review.text=adapter.itemCount.toString()
                    reviewre.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<reviewInfo>>, t: Throwable) {
                Log.d("get_review", "실패:${t.message}}")
            }
        })

        reg_review.setOnClickListener {
            val bundle=Bundle()
            var dialog:ReviewDialogFragment=ReviewDialogFragment().getInstance()
            dialog.arguments=bundle
            activity?.supportFragmentManager?.let{fragmentManager->
                dialog.show(
                        fragmentManager,"리뷰 등록"
                )
            }
        }

    }


}