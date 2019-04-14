package com.taoszu.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.taoszu.layoutmarker.MarkerHub

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MarkerHub.startMark(window)

    }
}
