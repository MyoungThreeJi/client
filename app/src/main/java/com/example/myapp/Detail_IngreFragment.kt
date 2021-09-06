package com.example.myapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.effectdialog.view.*

import kotlinx.android.synthetic.main.fragment_detail__ingre.*
import kotlinx.android.synthetic.main.fragment_detail_list.*
import kotlinx.android.synthetic.main.fragment_main_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.String.join

class Detail_IngreFragment : Fragment() {
    private lateinit var adapter:IngreAdapter

    var ints = mutableListOf<Integer>()
    var names= mutableListOf<String>()
    var averages= mutableListOf<Double>()
    var detections= mutableListOf<Double>()
    var images= mutableListOf<String>()
    var effects= mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     var root =inflater.inflate(R.layout.fragment_detail__ingre, container, false)



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        val po = bundle?.getInt("idpo")

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        inre.layoutManager = layoutManager
        val dividerItemDecoration =
                DividerItemDecoration(context, LinearLayoutManager(context).orientation)

        inre.addItemDecoration(dividerItemDecoration)


        var retrofit = Retrofit.Builder().baseUrl(ApiService.API_URL).addConverterFactory(
                GsonConverterFactory.create()).build()
        var apiService = retrofit.create(ApiService::class.java)
        var tests = apiService.get_id(po!!)
        tests.enqueue(object : Callback<padlist> {
            override fun onResponse(call: Call<padlist>, response: Response<padlist>) {
                if (response.isSuccessful) {
                    var mList = response.body()!!
                    //Log.e("id",mList.name.toString())
                    var inlist=mList.ingredients

                    for(i in inlist.indices){
                        ints.add(inlist.get(i).id!!)
                        names.add(inlist.get(i).name!!)
                      averages.add(inlist.get(i).average!!)
                        effects.add(inlist.get(i).sideEffect!!)
                         //list3.add(inlist.get(i).average!!)
                   }
                    Log.e("list1",ints.get(0).toString())
                    Log.e("namelist",names.get(0))
                    Log.e("averagelist",averages.get(0).toString())
                    Log.e("intslist",ints.toString())
                    ints.reverse()
                    Log.e("intslist",ints.toString())
                }
            }

            override fun onFailure(call: Call<padlist>, t: Throwable) {
                Log.e("D_tests", "OnFailuer+${t.message}")
            }
        })

        var retrofit1 = Retrofit.Builder().baseUrl(ApiService.API_URL).addConverterFactory(
                GsonConverterFactory.create()).build()
        var apiService1 = retrofit1.create(ApiService::class.java)
        var tests1 = apiService1.get_detection("json")
        tests1.enqueue(object : Callback<ArrayList<detectioninfo>> {
            override fun onResponse(call: Call<ArrayList<detectioninfo>>, response: Response<ArrayList<detectioninfo>>) {
                if (response.isSuccessful) {
                    var mList = response.body()!!
                    Log.e("detection", mList.get(0).pad.toString())

                    for (i in mList.indices) {
                        if ((mList.get(i).pad)!!.equals(po!!)) {

                            detections.add(mList.get(i).detection!!)

                        }

                    }
                    Log.e("success", detections.toString())
                    detections.reverse()
                    Log.e("success", detections.toString())
                    var image:String
                    for(i in detections.indices){
                        if(averages.get(i)<detections.get(i)){
                            images.add("고")
                        }
                        else{
                            images.add("저")
                        }

                    }
                    //l image:String?, val name:String?, val average: Double?, val detection: Double?)

                    var d= mutableListOf<parent>()
                    for(i in detections.indices){
                    var reallist = parent(images.get(i),names.get(i),averages.get(i),detections.get(i))
                    d.add(reallist)
                        }
                    Log.e("parentmake", d.toString())
                    adapter = IngreAdapter(d)

                    inre.adapter= adapter
                    var sum=0.0
                    for(i in detections.indices){
                        sum += detections.get(i)

                    }
                    sumin.setText(sum.toString())


                    adapter.setItemClickListener(object : IngreAdapter.ItemClickListener {
                        override fun onClick(view: View, position: Int) {
                            var dlg = AlertDialog.Builder(requireContext())
                            var dialogView = View.inflate(context, R.layout.effectdialog, null)
                            dlg.setView(dialogView)

                            var elist=effects.get(position).split(",")

                            var effect=""
                            for(e in elist){
                                effect=effect+"-"+e+"\n"
                            }
                            Log.d("effects",effect)

                            dialogView.effectds.setText(effect)

                            dlg.setPositiveButton("확인") { dialog, which -> }

                            dlg.show()



                        }})

                }
            }

            override fun onFailure(call: Call<ArrayList<detectioninfo>>, t: Throwable) {
                Log.e("D_tests", "OnFailuer+${t.message}")
            }
        })



    }




    }
