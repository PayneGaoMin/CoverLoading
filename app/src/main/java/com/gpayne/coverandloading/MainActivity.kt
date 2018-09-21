package com.gpayne.coverandloading

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.gpayne.cover_and_loading.dismissLoading
import com.gpayne.cover_and_loading.showLoading


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_title).setOnClickListener {
            showLoading("...")
            Handler().postDelayed({
                dismissLoading()
            },2000)
        }
    }
}
