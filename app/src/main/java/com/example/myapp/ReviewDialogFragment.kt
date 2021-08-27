package com.example.myapp


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.review_dialog.*
import kotlinx.android.synthetic.main.review_dialog.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReviewDialogFragment: DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?)
    : View? {
        val view=inflater.inflate(R.layout.review_dialog,container,false)
        val bundle=arguments

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var retrofit = Retrofit.Builder().baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        var apiService = retrofit.create(ApiService::class.java)

        view.post_review.setOnClickListener {
            var reviewData=p_reviewInfo(2,rat1.rating.toInt(),rat2.rating.toInt(),rat3.rating.toInt(),rat4.rating.toInt(),r_content.text.toString())
            var review=apiService.reg_review(reviewData)

            review.enqueue(object: Callback<p_reviewInfo> {
                override fun onResponse(call: Call<p_reviewInfo>, response: Response<p_reviewInfo>) {
                    if(response.isSuccessful){
                        Log.d("post_review","리뷰 등록 성공-${response.body().toString()}")
                        dialog!!.hide()

                        val fragmentManager=requireActivity().supportFragmentManager
                        var transaction:FragmentTransaction
                        val fragmentA=ReviewFragment()
                        transaction=fragmentManager.beginTransaction()

                        val bundle=Bundle()
                        fragmentA.arguments=bundle
                        transaction.add(R.id.container,fragmentA)
                        transaction.replace(R.id.container,fragmentA.apply{arguments=bundle})
                        transaction.commit()
                    }
                    else{
                        val statusCode=response.code()
                        Log.d("post_review","리뷰 등록 실패-${statusCode}")
                    }
                }

                override fun onFailure(call: Call<p_reviewInfo>, t: Throwable) {
                    Log.e("post_review","리뷰 등록 실패-${t.message}")
                }
            })
        }
    }
    fun getInstance():ReviewDialogFragment{
        return ReviewDialogFragment()
    }
}