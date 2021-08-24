package com.demo.carrouselview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.carrouselview.databinding.RowMovieBinding


class CarrouselView constructor(context: Context, attrs: AttributeSet) :
    RecyclerView(context, attrs) {

    private var mInilialized = false
    private var listMovies: ArrayList<Movie>? = null

    init {
        mInilialized = true
    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        super.setLayoutParams(params)
    }

    fun <T> setItems(list: ArrayList<T>) {

    }


    @JvmName("setItemsMovie")
    fun setItems(list: ArrayList<Movie>) {
        listMovies = list
    }

    fun createAdapter() {
        adapter = CarrouselViewAdapter(listMovies, context).apply { notify() }
    }

    fun showCarrousel() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        setHasFixedSize(true)
        requestLayout()
    }

    fun isInilialized() = mInilialized

}

class CarrouselViewAdapter(private val dataSet: ArrayList<Movie>?, private val mContext: Context) :
    RecyclerView.Adapter<CarrouselHolder>() {

    @JvmName("notifyData")
    fun notify() {
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrouselHolder {
        return CarrouselHolder.create(
            parent = parent,
            block = RowMovieBinding::inflate,
            context = mContext
        )
    }

    override fun onBindViewHolder(holder: CarrouselHolder, position: Int) {
        dataSet?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount() = dataSet?.size!!

}

class CarrouselHolder(private val biding: RowMovieBinding) : RecyclerView.ViewHolder(biding.root) {

    fun bind(itemPosition: Movie) {
        biding.root.apply {
            this.addView(
                ImageView(biding.root.context).apply {
                    this.setImageURI()
                }
            )

        }
    }

    companion object {
        inline fun create(
            context: Context,
            parent: ViewGroup, crossinline block: (
                inflater: LayoutInflater, container: ViewGroup,
                attack: Boolean
            ) -> RowMovieBinding
        ) = CarrouselHolder(block(LayoutInflater.from(context), parent, false))
    }
}