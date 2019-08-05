package kishimotovn.pocketworksWeather.features.landing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kishimotovn.pocketworksWeather.R
import kishimotovn.pocketworksWeather.base.BaseActivity
import kishimotovn.pocketworksWeather.databinding.ActivityLandingBinding
import kishimotovn.pocketworksWeather.features.home.HomeActivity
import kishimotovn.pocketworksWeather.features.search.SearchActivity


class LandingActivity: BaseActivity() {
    lateinit var binding: ActivityLandingBinding

    private val viewModel by lazy {
        ViewModelProviders.of(this, this.viewModelFactory).get(LandingViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        this.getComponent().inject(this)
        super.onCreate(savedInstanceState)
        this.binding = ActivityLandingBinding.inflate(LayoutInflater.from(this))
        this.setContentView(this.binding.root)
        this.initializeUI()
        this.bindUI()
    }

    private fun bindUI() {
        this.viewModel.cachedCities.observe(this, Observer { result ->
            result.onSuccess {
                this.routeToHome()
            }.onFailure {
                this.showAlert(this.getString(R.string.alert_error_title), it.localizedMessage)
            }
        })
    }

    private fun routeToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }

    private fun initializeUI() {
        val option = RequestOptions().centerCrop()
        Glide.with(this)
                .load(R.drawable.bg_main)
                .apply(option)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this.binding.bgMain)
    }
}