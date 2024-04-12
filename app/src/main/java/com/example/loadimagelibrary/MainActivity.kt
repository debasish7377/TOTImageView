package com.example.loadimagelibrary

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.totimageview.TOTImageView

class MainActivity : AppCompatActivity() {

    lateinit var imageView: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)

        //val imageUrl = "https://www.youtube.com/@thugsoftechnology"
        val imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRrTFrhr_-pYR74jUgOy7IerAoHAX3zPIZZcg&s"

        TOTImageView(imageView)
            .circle()
            .placeholder(R.drawable.avatar)
            .src(imageUrl)
            .load(this)

    }
}