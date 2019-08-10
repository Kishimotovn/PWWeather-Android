package kishimotovn.pocketworksWeather.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("image_url")
fun ImageView.setImageUrl(image: String?) {
    val options = RequestOptions().fitCenter()
    Glide.with(context).load(image).apply(options).into(this)
}