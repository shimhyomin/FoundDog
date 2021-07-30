package com.example.founddog.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.founddog.R
import com.example.founddog.model.PostDTO
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var item = intent.getParcelableExtra<PostDTO>("itemKey")

        detail_title.text = item!!.title.toString()
        Glide.with(this).load(item!!.imgUrl).into(img)
        content.text = item!!.content.toString()
    }
}