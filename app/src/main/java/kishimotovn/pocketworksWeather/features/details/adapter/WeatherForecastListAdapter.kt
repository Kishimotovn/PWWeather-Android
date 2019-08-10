package kishimotovn.pocketworksWeather.features.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kishimotovn.pocketworksWeather.data.local.models.PWUnitSystem
import kishimotovn.pocketworksWeather.data.remote.models.CityWeather
import kishimotovn.pocketworksWeather.databinding.ItemWindForecastBinding
import kishimotovn.pocketworksWeather.features.details.viewmodels.ForecastItemViewModel

class WeatherForecastItemDiffCallback: DiffUtil.ItemCallback<CityWeather>() {
    override fun areItemsTheSame(oldItem: CityWeather, newItem: CityWeather): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CityWeather, newItem: CityWeather): Boolean {
        return oldItem.name == newItem.name && oldItem.dt == newItem.dt
    }
}

class WeatherForecastListAdapter(var unitSystem: PWUnitSystem = PWUnitSystem.metric, var timezoneOffset: Int = 0): ListAdapter<CityWeather, WeatherForecastListAdapter.ViewHolder>(WeatherForecastItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWindForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = this.getItem(position)
        holder.bind(item, this.unitSystem, this.timezoneOffset)
    }

    class ViewHolder(val binding: ItemWindForecastBinding): RecyclerView.ViewHolder(binding.root) {
        var cityId: String? = null

        fun bind(item: CityWeather, unitSystem: PWUnitSystem, timezoneOffset: Int) {
            with(this.binding) {
                this.viewModel = ForecastItemViewModel(item, unitSystem, timezoneOffset)
                this.executePendingBindings()
            }
            this.cityId = item.id?.toString()
        }
    }
}