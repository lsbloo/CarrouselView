package com.demo.carrouselview

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes
import androidx.core.view.marginTop
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.carrouselview.databinding.RowMovieBinding


class CarrouselView constructor(context: Context, attrs: AttributeSet) :
    RecyclerView(context, attrs) {

    private var mInilialized = false
    private var listMovies: ArrayList<Movie>? = null
    private var windowManager: WindowManager? = null

    init {
        mInilialized = true

 //       setBackgroundColor(context.resources.getColor(R.color.fousc_black))
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
    fun setWindowManager(windowManager: WindowManager){
        this.windowManager = windowManager
    }
    fun showCarrousel() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        setHasFixedSize(true)
        showDecorators()
        requestLayout()

    }

    private fun showDecorators(){
        if(this.listMovies?.size == 1) {
            this.windowManager?.let {
                CarrouselDecoratorPrimary(R.dimen.space_decoration_recycler_view, it)
            }?.let { addItemDecoration(it) }
        }
    }

    fun isInilialized() = mInilialized

    internal inner class CarrouselDecoratorDots constructor(
        private val radius: Float,
        private val padding: Int,
        private val indicatorHeight: Int,
        @ColorInt private val colorActive: Int,
        @ColorInt private val colorInative: Int
    ): RecyclerView.ItemDecoration() {
        private val inativePaint = Paint()
        private val activePaint = Paint()
        private var indicatorItemPadding: Int? = null

        init {
            inativePaint.strokeCap = Paint.Cap.ROUND
            inativePaint.strokeWidth = Resources.getSystem().displayMetrics.density
            inativePaint.style = Paint.Style.STROKE
            inativePaint.isAntiAlias = true
            inativePaint.color =  colorInative

            activePaint.strokeCap = Paint.Cap.ROUND
            activePaint.strokeWidth = Resources.getSystem().displayMetrics.density
            activePaint.style = Paint.Style.FILL
            activePaint.isAntiAlias = true
            activePaint.color =  colorInative

            indicatorItemPadding = padding
        }

        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: State) {
            super.onDrawOver(c, parent, state)
            val adapter = parent.adapter ?: return

            val itemCount = adapter.itemCount

            val totLenght = radius * 2 * itemCount
            val paddingBetweenItems = 0.coerceAtLeast(itemCount - 1) * indicatorItemPadding!!

            //drawerActiveDots(c,)
        }

        private fun drawerInativeDots(canvas: Canvas, indicatorX: Float, indicatorY: Float, itemCounts: Int){
            val iterator = (0..itemCounts).iterator()
            if(iterator.hasNext()){ }
        }
        private fun drawerActiveDots(canvas: Canvas, indicatorX: Float, indicatorY: Float, itemCounts: Int){
            canvas.drawCircle(indicatorX, indicatorY, radius, activePaint)
        }
    }


    internal inner class CarrouselDecoratorPrimary constructor(
        @DimenRes private val space: Int,
        private val windowManager: WindowManager?,
    ) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View, parent:
            RecyclerView,
            state: State
        ) {
            val itemPosition = parent.getChildAdapterPosition(view)
            if (itemPosition == NO_POSITION) {
                return
            } else {
                val spacePixelSize = if (windowManager != null) {
                    val displayMetrics = DisplayMetrics()
                    windowManager.defaultDisplay.getMetrics(displayMetrics)
                    displayMetrics.widthPixels
                } else {
                    parent.context.resources.getDimension(R.dimen.space_decoration_recycler_view)
                }
                when (itemPosition) {
                    0 -> {
                        outRect.set(getOffsetPixelSize(parent, view), 0, spacePixelSize.toInt() / 3, 0)
                        parent.adapter!!.itemCount - 1
                        outRect.set(spacePixelSize.toInt() / 3, 0, getOffsetPixelSize(parent, view), 0)
                    }
                }
            }
        }

        private fun getOffsetPixelSize(parent: RecyclerView, view: View):
                Int {
            val orientationHelper = OrientationHelper.
            createHorizontalHelper(parent.layoutManager)
            return (orientationHelper.totalSpace - view.layoutParams.width) / 2
        }
    }
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
                setImageView(this, itemPosition, context)
        }
    }

    private fun setImageView(layout: LinearLayout, itemPosition: Movie, context: Context) {
        val imageView = ImageView(biding.root.context).apply {
            layoutParams = RelativeLayout.LayoutParams(300, 300).apply {
                addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
            }
            val margin = this.layoutParams as MarginLayoutParams
            margin.setMargins(4, 24, 4, 24)
        }

        layout.addView(imageView)
        Glide.with(context).load(itemPosition.imageMovie)
            .into(imageView)
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