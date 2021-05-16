package br.com.lucad.kotlinmarmitex.utils

import android.view.View
import android.widget.ImageView
import br.com.lucad.kotlinmarmitex.R
import com.bumptech.glide.Glide

class LoadImage {

    fun getImageFromFirebase(itemView: View, imageView: ImageView, url: String) {
        Glide.with(itemView)
            .load(url)
            .centerCrop() //4
            .placeholder(R.drawable.ic_baseline_food_bank_24) //5
            .error(R.drawable.ic_baseline_image_not_supported_24) //6
            .into(imageView)
    }
}