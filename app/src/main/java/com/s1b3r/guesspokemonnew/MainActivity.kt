package com.s1b3r.guesspokemonnew

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        UtilsWeb.getPkm(6){
            Log.d("","")
        }

    }
}
