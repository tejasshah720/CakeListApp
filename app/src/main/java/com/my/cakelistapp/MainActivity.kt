package com.my.cakelistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.my.cakelistapp.fragment.CakeListFragment

/**
 * Created by Tejas Shah on 27/03/19.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
