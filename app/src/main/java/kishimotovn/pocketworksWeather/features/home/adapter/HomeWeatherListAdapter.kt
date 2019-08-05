package kishimotovn.pocketworksWeather.features.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kishimotovn.pocketworksWeather.data.remote.models.CityWeather
import kishimotovn.pocketworksWeather.databinding.ItemCityWeatherBinding
import kishimotovn.pocketworksWeather.features.home.viewmodels.CityWeatherItemViewModel

class HomeWeatherItemDiffCallback: DiffUtil.ItemCallback<CityWeather>() {
    override fun areItemsTheSame(oldItem: CityWeather, newItem: CityWeather): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CityWeather, newItem: CityWeather): Boolean {
        return oldItem.name == newItem.name
    }
}

class HomeWeatherListAdapter(val delegate: Delegate?): ListAdapter<CityWeather, HomeWeatherListAdapter.ViewHolder>(HomeWeatherItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCityWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = this.getItem(position)
        val onClickListener = View.OnClickListener {
            this.delegate?.didSelect(item, position)
        }
        holder.bind(item, onClickListener)
    }

    class ViewHolder(val binding: ItemCityWeatherBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CityWeather, clickListener: View.OnClickListener) {
            with(this.binding) {
                this.viewModel = CityWeatherItemViewModel(item)
                this.clickListener = clickListener
                this.executePendingBindings()
            }
        }
    }

    interface Delegate {
        fun didSelect(item: CityWeather, index: Int)
    }
}