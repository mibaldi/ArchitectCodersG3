package com.mibaldi.brewing.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mibaldi.brewing.R

class DetailActivity : AppCompatActivity() {
    companion object {
        const val PUB = "DetailActivity:beer"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}
