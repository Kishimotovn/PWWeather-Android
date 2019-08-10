package kishimotovn.pocketworksWeather.features.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kishimotovn.pocketworksWeather.R
import kishimotovn.pocketworksWeather.base.BaseActivity
import kishimotovn.pocketworksWeather.data.local.models.PWCity
import kishimotovn.pocketworksWeather.data.local.models.PWUnitSystem
import kishimotovn.pocketworksWeather.data.remote.models.CityWeather
import kishimotovn.pocketworksWeather.databinding.ActivityHomeBinding
import kishimotovn.pocketworksWeather.features.details.DetailsActivity
import kishimotovn.pocketworksWeather.features.home.adapter.HomeWeatherListAdapter
import kishimotovn.pocketworksWeather.features.home.adapter.SwipeToDeleteCallback
import kishimotovn.pocketworksWeather.features.search.SearchActivity
import kishimotovn.pocketworksWeather.features.search.SearchActivity.Companion.SEARCH_CITY_RESULT_INTENT_KEY
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
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.CITY_WEATHER_DATA, item)
        this.startActivity(intent)
    }

    private fun bindUI() {
        this.viewModel.isLoading.observe(this, Observer { isLoading ->
            this.binding.activityIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
        this.viewModel.userCityWeather.observe(this, Observer {
            Log.d("HomeActivity", "got list ${it}")
            this.adapter.submitList(it)
        })
        this.viewModel.unitSystem.observe(this, Observer {
            when (it) {
                PWUnitSystem.metric -> {
                    this.binding.celciusButton.alpha = 1.0f
                    this.binding.fahrenheitButton.alpha = 0.5f
                }
                PWUnitSystem.imperial -> {
                    this.binding.celciusButton.alpha = 0.5f
                    this.binding.fahrenheitButton.alpha = 1.0f
                }
            }
            this.adapter.unitSystem = it
            this.adapter.notifyDataSetChanged()
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
            val swipeHandler = object: SwipeToDeleteCallback(context) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    (viewHolder as? HomeWeatherListAdapter.ViewHolder)?.cityId?.let {
                        context.viewModel.removeCity(it)
                    }
                    Log.d("HomeActivity", "swiped ${viewHolder.adapterPosition}")
                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(this.userCityList)
            this.celciusButton.setOnClickListener {
                context.viewModel.changeUnitSystem(PWUnitSystem.metric)
            }
            this.fahrenheitButton.setOnClickListener {
                context.viewModel.changeUnitSystem(PWUnitSystem.imperial)
            }
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
                    this.viewModel.addCity(it)
                }
            }
        }
    }
}
