package com.example.myapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.net.URLDecoder

class DetailListFragment(position: Int) : Fragment() {
    private lateinit var adapter: ListdetailAdapter
    var idpo: Int = position
    var names = mutableListOf<String>()
    var averages = mutableListOf<Double>()
    var maxs = mutableListOf<Double>()
    var mins = mutableListOf<Double>()
    var detections = mutableListOf<Double>()
    private lateinit var scoreList: ArrayList<Float>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(R.layout.fragment_detail_list, container, false)


        Log.d("id", "id=${idpo}")


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        class URLtoBitmapTask() : AsyncTask<Void, Void, Bitmap>() {
            //액티비티에서 설정해줌
            lateinit var url: URL
            override fun doInBackground(vararg params: Void?): Bitmap {
                val bitmap = BitmapFactory.decodeStream(url.openStream())
                return bitmap
            }

            override fun onPreExecute() {
                super.onPreExecute()

            }

            override fun onPostExecute(result: Bitmap) {
                super.onPostExecute(result)
            }
        }


        val bundle = arguments
        val po = bundle?.getInt("position")

        Log.e("bundel", po.toString())

        val layoutManager = LinearLayoutManager(context)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        recy.layoutManager = layoutManager

        adapter = ListdetailAdapter()
        recy.adapter = adapter

        var retrofit = Retrofit.Builder().baseUrl(ApiService.API_URL).addConverterFactory(
                GsonConverterFactory.create()).build()
        var apiService = retrofit.create(ApiService::class.java)

        var tests = apiService.get_id(idpo)
        var reviews = apiService.get_review("json")

        tests.enqueue(object : Callback<padlist> {
            override fun onResponse(call: Call<padlist>, response: Response<padlist>) {
                if (response.isSuccessful) {
                    var mList = response.body()!!
                    detailname.setText(mList.name.toString());
                    //detailimg.setImageDrawable(mList.image)
                    /* var i= mList.image
                     var n= i!!.indexOf("i")
                     var re=i.toString().substring((n+3)!!)
                     Log.e("Re",re)
                     var image_task: URLtoBitmapTask = URLtoBitmapTask()
                     image_task = URLtoBitmapTask().apply {
                         url = URL(re)
                     }
                     var bitmap: Bitmap = image_task.execute().get()
                     detailimg.setImageBitmap(bitmap)*/

                    Log.e("Re", mList.image!!)
                    Log.e("Re", mList.image!!.substring(ApiService.API_URL.length + 1))
                    var a = URLDecoder.decode(mList.image!!.substring(ApiService.API_URL.length + 1), "utf-8");
                    Log.e("Result", a)
                    Glide.with(view).load(a).into(detailimg)
                    Log.d("imgUrl", mList.image!!.substring(ApiService.API_URL.length + 1))
                    drank.setText(mList.rank.toString())
                    dscore.setText(mList.safeScore.toString())

                    detailma.setText(mList.manufacturer.toString());
                    Log.e("D_tests", mList.name.toString())

                    var inlist = mList.ingredients

                    for (i in inlist.indices) {
                        names.add(inlist.get(i).name!!)
                        maxs.add(inlist.get(i).max!!)
                        mins.add(inlist.get(i).min!!)
                        averages.add(inlist.get(i).average!!)
                    }
                }
            }

            override fun onFailure(call: Call<padlist>, t: Throwable) {
                Log.e("D_tests", "OnFailuer+${t.message}")
            }
        })

        var tests1 = apiService.get_detection("json")
        tests1.enqueue(object : Callback<ArrayList<detectioninfo>> {
            override fun onResponse(call: Call<ArrayList<detectioninfo>>, response: Response<ArrayList<detectioninfo>>) {
                if (response.isSuccessful) {
                    var mList = response.body()!!
                    //Log.e("detection", mList.get(0).pad.toString())

                    for (i in mList.indices) {
                        if ((mList.get(i).pad)!!.equals(idpo)) {

                            detections.add(mList.get(i).detection!!)
                        }
                        Log.e("success", detections.toString())
                        detections.reverse()
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<detectioninfo>>, t: Throwable) {
                Log.e("D_tests", "OnFailuer+${t.message}")
            }
        })

        reviews.enqueue(object : Callback<List<reviewInfo>> {
            override fun onResponse(
                    call: Call<List<reviewInfo>>,
                    response: Response<List<reviewInfo>>
            ) {
                if (response.isSuccessful) {
                    var reviewList = response.body()!!
                    scoreList = ArrayList()
                    var score: Float? = null
                    var sum = 0.0

                    for (r in reviewList) {
                        if (r.pad == idpo) {
                            score = (r.star1!! + r.star2!! + r.star3!! + r.star4!!) / 4
                            scoreList!!.add(score)
                            sum = sum + score
                        }
                    }
                    //Log.d("scores",scoreList.toString())
                    review_score.text = sum.div(scoreList.size).toString()
                    score_star.rating = sum.div(scoreList.size).toFloat()
                    //Log.d("scores",sum.div(scoreList.size).toString())
                }
            }

            override fun onFailure(call: Call<List<reviewInfo>>, t: Throwable) {
                Log.d("get_review", "실패:${t.message}}")
            }
        })

        adapter.items.add(List_IngreItem("핵산", 8, 8, 8))
        adapter.items.add(List_IngreItem("그로포", 8, 8, 8))
        adapter.notifyDataSetChanged()



        gotodi.setOnClickListener {
            val fragmentManager3 = requireActivity().supportFragmentManager
            var transaction3: FragmentTransaction
            val fragmentA = Detail_IngreFragment()
            transaction3 = fragmentManager3.beginTransaction()
            val bundle = Bundle()
            bundle.putInt("idpo", idpo)
            fragmentA.arguments = bundle
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, Detail_IngreFragment())
            transaction.replace(R.id.container, Detail_IngreFragment().apply { arguments = bundle })
            transaction.commit()


        }


        gotoreview.setOnClickListener {
            val fragmentManager2 = requireActivity().supportFragmentManager
            var transaction2: FragmentTransaction
            val fragmentA = ReviewFragment(idpo)
            transaction2 = fragmentManager2.beginTransaction()
            val bundle = Bundle()

            bundle.putString("name1", "h")
            fragmentA.arguments = bundle
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, fragmentA)
            transaction.replace(R.id.container, fragmentA.apply { arguments = bundle })
            transaction.commit()


        }


    }


}
