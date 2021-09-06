package com.example.myapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapp.R
import kotlinx.android.synthetic.main.effectdialog.view.*
import kotlinx.android.synthetic.main.effectdialog.view.effectds
import kotlinx.android.synthetic.main.fragment_detail_list.*


import kotlinx.android.synthetic.main.fragment_main_list.*
import kotlinx.android.synthetic.main.fragment_main_list.view.*
import kotlinx.android.synthetic.main.searchdialog.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLDecoder


class MainListFragment : Fragment() {
    var images = mutableListOf<String>()
    var ints = mutableListOf<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root=inflater.inflate(R.layout.fragment_main_list, container, false)
        val layoutManager = LinearLayoutManager(activity)
        //layoutManager.setReverseLayout(true)
        //layoutManager.setStackFromEnd(true)
        root.recyclerView1.layoutManager = layoutManager
        lateinit var adapter: padInfoAdapter

        //recyclerView1.layoutManager = LinearLayoutManager(requireContext())


        //recyclerView1.adapter = adapter

        // adapter.items.add(padInfo(8, "유한컴벌리", "생리대이름", "h"))
        // adapter.items.add(padInfo(9, "유한컴벌리", "유한", 7))
        //adapter.notifyDataSetChanged()

        var retrofit = Retrofit.Builder().baseUrl(ApiService.API_URL).addConverterFactory(
                GsonConverterFactory.create()).build()
        var apiService = retrofit.create(ApiService::class.java)
        var tests = apiService.get_padid("json")

        tests.enqueue(object : Callback<ArrayList<padlist>> {
            override fun onResponse(call: Call<ArrayList<padlist>>, response: Response<ArrayList<padlist>>) {
                if (response.isSuccessful) {
                    var mList = response.body()!!
                    for(i in mList.indices){
                        ints.add(mList.get(i).id!!)
                        images.add(mList.get(i).image!!)
                    }

                    //Log.e("rank",mList.get(0).image!!.toString())
                   // Log.e("rank",mList.get(0).image!!.substring(ApiService.API_URL.length+2).toString())

                    //recyclerView1.adapter = padInfoAdapter(mList)
                    adapter = padInfoAdapter(mList)
                    root.recyclerView1.adapter= adapter
                    adapter.setItemClickListener(object : padInfoAdapter.ItemClickListener {
                        override fun onClick(view: View, position: Int) {
                            Log.e("rank",mList.get(position).rank!!.toString())
                            val fragmentA = DetailListFragment(ints.get(position))
                            //Log.d("position","position=${position}")

                            val bundle = Bundle()

                            //bundle.putInt("position",position)
                            fragmentA.arguments=bundle
                            val transaction = activity!!.supportFragmentManager.beginTransaction()
                            //transaction.add(R.id.container,DetailListFragment())
                            transaction.add(R.id.container,fragmentA)
                            //transaction.replace(R.id.container, DetailListFragment().apply { arguments = bundle })
                            transaction.replace(R.id.container, fragmentA.apply { arguments = bundle }).addToBackStack(null)
                            transaction.commit()



                        }


                    })
                }
            }

            override fun onFailure(call: Call<ArrayList<padlist>>, t: Throwable) {
                Log.e("D_tests", "OnFailuer+${t.message}")
            }
        }

        )

        root.rankinfos.setOnClickListener{

            var dlg = AlertDialog.Builder(requireContext())
            var dialogView = View.inflate(context, R.layout.rankinfodialog, null)
            dlg.setView(dialogView)
            dlg.setPositiveButton("확인") { dialog, which ->}
            dlg.show()
        }

root.searchlist.setOnClickListener{
    root.recyclerView1.adapter= adapter
    adapter.clearItem()
  var dlg = AlertDialog.Builder(requireContext())
    var dialogView = View.inflate(context, R.layout.searchdialog, null)
    dlg.setView(dialogView)
    var what=dialogView.dtext.getText()

    dlg.setPositiveButton("검색") { dialog, which ->


    var retrofit = Retrofit.Builder().baseUrl(ApiService.API_URL).addConverterFactory(
                GsonConverterFactory.create()).build()
        var apiService = retrofit.create(ApiService::class.java)
        var tests = apiService.search_pad(what.toString())

        tests.enqueue(object : Callback<ArrayList<padlist>> {
            override fun onResponse(call: Call<ArrayList<padlist>>, response: Response<ArrayList<padlist>>) {
                if (response.isSuccessful) {
                    var mList = response.body()!!


                    adapter = padInfoAdapter(mList)

                    root.recyclerView1.adapter= adapter


                }
            }

            override fun onFailure(call: Call<ArrayList<padlist>>, t: Throwable) {
                Log.e("D_tests", "OnFailuer+${t.message}")
            }
        }

        )


    }



    dlg.show()


}

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}

