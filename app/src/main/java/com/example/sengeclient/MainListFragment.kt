package com.example.sengeclient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager


import kotlinx.android.synthetic.main.fragment_main_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainListFragment : Fragment() {
    lateinit var adapter: padInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root=inflater.inflate(R.layout.fragment_main_list, container, false)



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       val layoutManager = LinearLayoutManager(activity)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        recyclerView1.layoutManager = layoutManager
        //recyclerView1.layoutManager = LinearLayoutManager(requireContext())


        //recyclerView1.adapter = adapter

        // adapter.items.add(padInfo(8, "유한컴벌리", "생리대이름", "h"))
        // adapter.items.add(padInfo(9, "유한컴벌리", "유한", 7))
        //adapter.notifyDataSetChanged()

        var retrofit = Retrofit.Builder().baseUrl(ApiService.API_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        var apiService = retrofit.create(ApiService::class.java)
        var tests = apiService.get_pad("json")
        tests.enqueue(object : Callback<List<padInfo>> {
            override fun onResponse(call: Call<List<padInfo>>, response: Response<List<padInfo>>) {
                if (response.isSuccessful) {
                    var mList = response.body()!!


                        //recyclerView1.adapter = padInfoAdapter(mList)
                    adapter = padInfoAdapter(mList)
                    recyclerView1.adapter= adapter


                    adapter.setItemClickListener(object : padInfoAdapter.ItemClickListener {
                        override fun onClick(view: View, position: Int) {
                            val fragmentManager3 = activity!!.supportFragmentManager
                            var transaction3: FragmentTransaction
                            val fragmentA = DetailListFragment()
                            transaction3 = fragmentManager3.beginTransaction()
                            val bundle = Bundle()

                            bundle.putString("name1", "h")
                            fragmentA.arguments=bundle
                            val transaction = activity!!.supportFragmentManager.beginTransaction()
                            transaction.add(R.id.container,DetailListFragment())
                            transaction.replace(R.id.container, DetailListFragment().apply { arguments = bundle })
                            transaction.commit()



                        }


                    })






                }
            }

            override fun onFailure(call: Call<List<padInfo>>, t: Throwable) {
                Log.e("D_tests", "OnFailuer+${t.message}")
            }
        }

        )


    }
}

