package com.example.myapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        with(supportFragmentManager.beginTransaction()) {
            val fragment5 = MainListFragment()
            replace(R.id.container, fragment5)
            commit()
        }

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.tab1 -> {
                    with(supportFragmentManager.beginTransaction()) {
                        val fragment5 = Map()
                        replace(R.id.container, fragment5)
                        commit()
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab2 -> {
                    with(supportFragmentManager.beginTransaction()) {
                        val fragment4 = MainListFragment()
                        replace(R.id.container, fragment4)
                        commit()
                    }
                    return@setOnNavigationItemSelectedListener true
                }


            }
            return@setOnNavigationItemSelectedListener false
        }
    }
}