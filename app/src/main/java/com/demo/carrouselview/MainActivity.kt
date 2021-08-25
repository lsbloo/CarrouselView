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
        movies.add(Movie("https://upload.wikimedia.org/wikipedia/pt/1/1a/Spider-Man_2.jpg"))
        movies.add(Movie("https://upload.wikimedia.org/wikipedia/pt/1/1a/Spider-Man_2.jpg"))
        movies.add(Movie("https://upload.wikimedia.org/wikipedia/pt/1/1a/Spider-Man_2.jpg"))
        movies.add(Movie("https://upload.wikimedia.org/wikipedia/pt/1/1a/Spider-Man_2.jpg"))
        movies.add(Movie("https://upload.wikimedia.org/wikipedia/pt/1/1a/Spider-Man_2.jpg"))
        movies.add(Movie("https://upload.wikimedia.org/wikipedia/pt/1/1a/Spider-Man_2.jpg"))

        //movies.add(Movie(""))
        binding?.carrousel?.apply {
            setItems(movies)
            setWindowManager(windowManager)
            createAdapter()
            showCarrousel()
        }
    }
}