package com.elyeproj.demoglide

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MyImageRequestListener.Callback {
    override fun onFailure(message: String?) {
        Toast.makeText(this, "Fail to load: $message", Toast.LENGTH_SHORT).show()
        my_image_view.isClickable = true
    }

    override fun onSuccess(dataSource: String) {
        Toast.makeText(this, "Loaded from: $dataSource", Toast.LENGTH_SHORT).show()
        my_image_view.isClickable = true
    }

    private val imageList = listOf(
            "https://images.pexels.com/photos/46239/salmon-dish-food-meal-46239.jpeg",
            "https://flybubble.com/media/wysiwyg/images/home/mainpage-box-5L.jpg",
            "https://i.imgur.com/FTBSFo7.jpg"
    )

    private var imageIndex = 0
        set(value) {
            field = value % imageList.size
        }

    private var oldImageUrl: String = imageList[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        my_image_view.setOnClickListener {
            loadImage(imageList[imageIndex++])
            my_image_view.isClickable = false
        }
    }

    private fun loadImage(url: String) {

        val requestOption = RequestOptions()
                .centerCrop()
                .transforms(CenterCrop(), RoundedCorners(1000))

        Glide.with(this).load(MyImageModel(url))
                .transition(DrawableTransitionOptions.withCrossFade())
                .thumbnail(
                        Glide.with(this)
                                .load(MyImageModel(oldImageUrl))
                                .apply(requestOption))
                .apply(requestOption)
                .listener(MyImageRequestListener(this))
                .into(my_image_view)

        oldImageUrl = url
    }
}
