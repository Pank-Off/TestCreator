package ru.punkoff.testforeveryone.data

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GlideLoader {
    companion object {
        fun loadImage(context: Context, url: Uri, imageView: ImageView) {
            Glide.with(context)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
        }
    }
}