package kishimotovn.pocketworksWeather.features.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kishimotovn.pocketworksWeather.data.local.models.PWUnitSystem
import kishimotovn.pocketworksWeather.data.remote.models.CityWeather
import kishimotovn.pocketworksWeather.databinding.ItemCityWeatherBinding
import kishimotovn.pocketworksWeather.features.home.viewmodels.CityWeatherItemViewModel

class HomeWeatherItemDiffCallback: DiffUtil.ItemCallback<CityWeather>() {
    override fun areItemsTheSame(oldItem: CityWeather, newItem: CityWeather): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CityWeather, newItem: CityWeather): Boolean {
        return oldItem.name == newItem.name && oldItem.dt == newItem.dt
    }
}

class HomeWeatherListAdapter(val delegate: Delegate?, var unitSystem: PWUnitSystem = PWUnitSystem.metric): ListAdapter<CityWeather, HomeWeatherListAdapter.ViewHolder>(HomeWeatherItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCityWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = this.getItem(position)
        val onClickListener = View.OnClickListener {
            this.delegate?.didSelect(item, position)
        }
        holder.bind(item, this.unitSystem, onClickListener)
    }

    class ViewHolder(val binding: ItemCityWeatherBinding): RecyclerView.ViewHolder(binding.root) {
        var cityId: String? = null

        fun bind(item: CityWeather, unitSystem: PWUnitSystem, clickListener: View.OnClickListener) {
            with(this.binding) {
                this.viewModel = CityWeatherItemViewModel(item, unitSystem)
                this.clickListener = clickListener
                this.executePendingBindings()
            }
            this.cityId = item.id?.toString()
        }
    }

    interface Delegate {
        fun didSelect(item: CityWeather, index: Int)
    }
}