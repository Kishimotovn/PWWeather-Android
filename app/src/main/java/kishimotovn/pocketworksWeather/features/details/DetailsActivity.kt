package kishimotovn.pocketworksWeather.features.details

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kishimotovn.pocketworksWeather.R
import kishimotovn.pocketworksWeather.base.BaseActivity
import kishimotovn.pocketworksWeather.databinding.ActivityDetailsBinding
import kishimotovn.pocketworksWeather.features.home.HomeViewModel
import kishimotovn.pocketworksWeather.features.shared.viewmodels.HeaderBarViewModel

class DetailsActivity : BaseActivity() {
    lateinit var binding: ActivityDetailsBinding

    private val viewModel by lazy {
        ViewModelProviders.of(this, this.viewModelFactory).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        this.getComponent().inject(this)
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailsBinding.inflate(LayoutInflater.from(this))
        this.setContentView(this.binding.root)
        this.initializeUI()
    }

    private fun initializeUI() {
        val context = this

        with(this.binding) {
            val option = RequestOptions().centerCrop()
            Glide.with(context)
                    .load(R.drawable.bg_main)
                    .apply(option)
                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .into(this.bgMain)
//            this.headerBar.viewModel = HeaderBarViewModel("Open Weather", null)
        }
    }
}
