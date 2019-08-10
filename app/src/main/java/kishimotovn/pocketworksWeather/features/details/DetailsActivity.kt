package kishimotovn.pocketworksWeather.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kishimotovn.pocketworksWeather.R
import kishimotovn.pocketworksWeather.base.BaseActivity
import kishimotovn.pocketworksWeather.data.remote.models.CityWeather
import kishimotovn.pocketworksWeather.databinding.ActivityDetailsBinding
import kishimotovn.pocketworksWeather.features.details.adapter.WeatherForecastListAdapter

class DetailsActivity : BaseActivity() {
    lateinit var binding: ActivityDetailsBinding

    private val viewModel by lazy {
        ViewModelProviders.of(this, this.viewModelFactory).get(DetailsViewModel::class.java)
    }
    private val adapter = WeatherForecastListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        this.getComponent().inject(this)
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailsBinding.inflate(LayoutInflater.from(this))
        this.setContentView(this.binding.root)
        this.initializeUI()
        this.bindUI()
        this.loadWeather()
        this.viewModel.loadForecast()
    }

    private fun loadWeather() {
        this.intent?.extras?.getParcelable<CityWeather>(CITY_WEATHER_DATA)?.let {
            this.viewModel.loadCityWeather(it)
        }
    }

    private fun bindUI() {
        this.viewModel.isLoadingForecast.observe(this, Observer {
            this.binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
        this.viewModel.forecastWeatherData.observe(this, Observer {
            this.adapter.unitSystem = this.viewModel.currentUnitSystem
            this.adapter.timezoneOffset = this.viewModel.cityWeather?.sys?.timezone ?: 0
            this.adapter.submitList(it)
        })
    }

    private fun initializeUI() {
        val context = this

        with(this.binding) {
            val option = RequestOptions().centerCrop()
            Glide.with(context)
                    .load(R.drawable.bg_main)
                    .apply(option)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(this.bgMain)

            this.listButton.setOnClickListener { context.onBackPressed() }
            this.viewModel = context.viewModel
            this.forecastRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
            this.forecastRecyclerView.adapter = context.adapter
            this.executePendingBindings()
        }
    }

    companion object {
        const val CITY_WEATHER_DATA = "cityWeather"
    }
}
