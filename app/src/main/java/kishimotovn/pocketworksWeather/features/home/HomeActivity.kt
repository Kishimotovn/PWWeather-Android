package kishimotovn.pocketworksWeather.features.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kishimotovn.pocketworksWeather.R
import kishimotovn.pocketworksWeather.base.BaseActivity
import kishimotovn.pocketworksWeather.data.local.models.PWCity
import kishimotovn.pocketworksWeather.data.remote.models.CityWeather
import kishimotovn.pocketworksWeather.databinding.ActivityHomeBinding
import kishimotovn.pocketworksWeather.features.home.adapter.HomeWeatherListAdapter
import kishimotovn.pocketworksWeather.features.landing.LandingViewModel
import kishimotovn.pocketworksWeather.features.search.SearchActivity
import kishimotovn.pocketworksWeather.features.search.SearchActivity.Companion.SEARCH_CITY_RESULT_INTENT_KEY
import kishimotovn.pocketworksWeather.features.search.adapter.SearchResultListAdapter
import kishimotovn.pocketworksWeather.features.shared.viewmodels.HeaderBarViewModel

class HomeActivity : BaseActivity(), HomeWeatherListAdapter.Delegate {
    lateinit var binding: ActivityHomeBinding

    private val viewModel by lazy {
        ViewModelProviders.of(this, this.viewModelFactory).get(HomeViewModel::class.java)
    }
    private val adapter = HomeWeatherListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        this.getComponent().inject(this)
        super.onCreate(savedInstanceState)
        this.binding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
        this.setContentView(this.binding.root)
        this.initializeUI()
        this.bindUI()
        this.viewModel.fetchWeatherDataForMyCities()
    }

    override fun didSelect(item: CityWeather, index: Int) {

    }

    private fun bindUI() {
        this.viewModel.isLoading.observe(this, Observer { isLoading ->
            this.binding.activityIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
        this.viewModel.userCityWeather.observe(this, Observer {
            Log.d("HomeActivity", "got list ${it}")
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
            this.headerBar.viewModel = HeaderBarViewModel("Open Weather", null)
            this.addCityButton.setOnClickListener {
                context.goToSearch()
            }
            this.userCityList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.userCityList.adapter = context.adapter
            this.executePendingBindings()
        }
    }

    private fun goToSearch() {
        val intent = Intent(this, SearchActivity::class.java)
        this.startActivityForResult(intent, SearchActivity.SEARCH_CITY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SearchActivity.SEARCH_CITY_REQUEST_CODE) {
            if (resultCode == SearchActivity.SEARCH_CITY_FINISH_RESULT_CODE) {
                data?.getParcelableExtra<PWCity>(SEARCH_CITY_RESULT_INTENT_KEY)?.let {
                    Log.d("HomeActivity", "Finished searching for city: ${it.name}")
                }
            }
        }
    }
}
