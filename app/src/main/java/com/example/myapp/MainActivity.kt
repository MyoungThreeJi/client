package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_map_button.view.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewPager()
    }

    private fun createView(tabName:String): View {
        var tabView=LayoutInflater.from(applicationContext).inflate(R.layout.custom_map_button,null)

        tabView.tab_text.text=tabName

        when(tabName){
            "생리대 맵"->{
                tabView.tab_img.setImageResource(R.drawable.map)
                return tabView
            }
            "생리대 성분분석"->{
                tabView.tab_img.setImageResource(R.drawable.ranking)
                return tabView
            }
            else->{
                return tabView
            }
        }
   }
    private fun initViewPager() {
        tab_layout.getTabAt(0)?.setCustomView(createView("생리대 맵"))
        tab_layout.getTabAt(1)?.setCustomView(createView("생리대 성분분석"))
    }
}