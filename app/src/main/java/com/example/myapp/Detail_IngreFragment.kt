package com.example.myapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.fragment_detail__ingre.*
import kotlinx.android.synthetic.main.fragment_detail_list.*
import kotlinx.android.synthetic.main.fragment_main_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Detail_IngreFragment : Fragment() {
    private lateinit var adapter:IngreAdapter
    lateinit var list1: ArrayList<Integer>
    var ints = mutableListOf<Integer>()
    var names= mutableListOf<String>()
    var averages= mutableListOf<Double>()

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

        lateinit var list2: ArrayList<String>
        lateinit var list3: ArrayList<Double>
        lateinit var list4: ArrayList<Double>

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
                    Log.e("id",inlist.get(0).id.toString())
                    Log.e("id",inlist.toString())


                    for(i in 0 until 15){
                        ints.add(inlist.get(i).id!!)
                        names.add(inlist.get(i).name!!)
                      averages.add(inlist.get(i).average!!)
                         //list3.add(inlist.get(i).average!!)
                   }
                    Log.e("list1",ints.get(0).toString())
                    Log.e("namelist",names.get(0))
                    Log.e("averagelist",averages.get(0).toString())
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
                    Log.e("detection",mList.get(0).pad.toString())
                  for(j in mList.indices) {
                        if((mList.get(j).pad)!!.equals(po!!)){
                            Log.e("detection",mList.get(0).pad.toString())
                           if((mList.get(j).ingredient)!!.equals(ints.get(j))){
                               // list4.add(mList.get(j).detection!!)
                               Log.e("success",mList.get(0).pad.toString())
                            }


                        }

                                }
                    /*
                lateinit var d:ArrayList<parent>
                    for(i in list4.indices){
                    var reallist = parent("Dd",list2.get(i),list3.get(i),list4.get(i))
                    d.add(reallist)
                        }
                    adapter = IngreAdapter(d)

                    inre.adapter= adapter*/
                }
            }

            override fun onFailure(call: Call<ArrayList<detectioninfo>>, t: Throwable) {
                Log.e("D_tests", "OnFailuer+${t.message}")
            }
        })

     /*   var sum=0.0
        for(i in list4.indices){
            sum += list4.get(i)

        }
        sumin.setText(sum.toString())*/



    }








    }
