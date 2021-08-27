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
import kotlinx.android.synthetic.main.fragment_main_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class DetailListFragment(position:Int) : Fragment() {
    private lateinit var adapter:ListdetailAdapter
    var idpo:Int=442-position

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var root =inflater.inflate(R.layout.fragment_detail_list, container, false)


        Log.d("id","id=${idpo}")


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        class URLtoBitmapTask() : AsyncTask<Void, Void, Bitmap>() {
            //액티비티에서 설정해줌
            lateinit var url:URL
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

 Log.e("bundel",po.toString())

        val layoutManager = LinearLayoutManager(context)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        recy.layoutManager = layoutManager

        adapter = ListdetailAdapter()
        recy.adapter = adapter

        var retrofit = Retrofit.Builder().baseUrl(ApiService.API_URL).addConverterFactory(
                GsonConverterFactory.create()).build()
        var apiService = retrofit.create(ApiService::class.java)

        var tests=apiService.get_id(idpo)

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
                    detailma.setText(mList.manufacturer.toString());
                    Log.e("D_tests",mList.name.toString())
                }
            }

            override fun onFailure(call: Call<padlist>, t: Throwable) {
                Log.e("D_tests", "OnFailuer+${t.message}")
            }
        })


        adapter.items.add(List_IngreItem("핵산",8,8,8))
        adapter.items.add(List_IngreItem("그로포",8,8,8))
        adapter.notifyDataSetChanged()



        gotodi.setOnClickListener {
            val fragmentManager3 = requireActivity().supportFragmentManager
            var transaction3: FragmentTransaction
            val fragmentA = Detail_IngreFragment()
            transaction3 = fragmentManager3.beginTransaction()
            val bundle = Bundle()
            bundle.putInt("idpo", idpo)
            fragmentA.arguments=bundle
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.container,Detail_IngreFragment())
            transaction.replace(R.id.container, Detail_IngreFragment().apply { arguments = bundle })
            transaction.commit()


        }


        gotoreview.setOnClickListener {
            val fragmentManager2 = requireActivity().supportFragmentManager
            var transaction2: FragmentTransaction
            val fragmentA = ReviewFragment()
            transaction2 = fragmentManager2.beginTransaction()
            val bundle = Bundle()

            bundle.putString("name1", "h")
            fragmentA.arguments=bundle
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.container,ReviewFragment())
            transaction.replace(R.id.container, ReviewFragment().apply { arguments = bundle })
            transaction.commit()


        }


    }



}
