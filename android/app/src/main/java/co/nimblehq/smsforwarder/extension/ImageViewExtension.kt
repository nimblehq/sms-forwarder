package co.nimblehq.smsforwarder.extension

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import co.nimblehq.smsforwarder.R
import com.bumptech.glide.load.engine.DiskCacheStrategy
import co.nimblehq.smsforwarder.di.modules.GlideApp

/**
 * Provide extension functions relates to ImageView and loading image mechanism.
 */

fun ImageView.loadImage(url: String) {
    GlideApp.with(context)
            .load(url)
            .placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.black_20a)))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .into(this)
}
