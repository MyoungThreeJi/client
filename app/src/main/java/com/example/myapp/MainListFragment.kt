package com.example.myapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.R


import kotlinx.android.synthetic.main.fragment_main_list.*
import kotlinx.android.synthetic.main.fragment_main_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root=inflater.inflate(R.layout.fragment_main_list, container, false)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
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

        tests.enqueue(object : Callback<List<padlist>> {
            override fun onResponse(call: Call<List<padlist>>, response: Response<List<padlist>>) {
                if (response.isSuccessful) {
                    var mList = response.body()!!


                    //recyclerView1.adapter = padInfoAdapter(mList)
                    adapter = padInfoAdapter(mList)
                    root.recyclerView1.adapter= adapter
                    adapter.setItemClickListener(object : padInfoAdapter.ItemClickListener {
                        override fun onClick(view: View, position: Int) {

                            val fragmentA = DetailListFragment(position)
                            Log.d("position","position=${position}")

                            val bundle = Bundle()

                            //bundle.putInt("position",position)
                            fragmentA.arguments=bundle
                            val transaction = activity!!.supportFragmentManager.beginTransaction()
                            //transaction.add(R.id.container,DetailListFragment())
                            transaction.add(R.id.container,fragmentA)
                            //transaction.replace(R.id.container, DetailListFragment().apply { arguments = bundle })
                            transaction.replace(R.id.container, fragmentA.apply { arguments = bundle })
                            transaction.commit()



                        }


                    })
                }
            }

            override fun onFailure(call: Call<List<padlist>>, t: Throwable) {
                Log.e("D_tests", "OnFailuer+${t.message}")
            }
        }

        )





        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}

