package com.demo.carrouselview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.carrouselview.databinding.ActivityMainBinding
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding


        val movies = ArrayList<Movie>()
        movies.add(Movie("Titanic"))
        movies.add(Movie("Naruto"))
        movies.add(Movie("Naruto Shippuden"))
        binding?.carrousel?.apply {
            setItems(movies)
            createAdapter()
            showCarrousel()
        }
    }
}