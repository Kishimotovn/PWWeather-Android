package kishimotovn.pocketworksWeather.features.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kishimotovn.pocketworksWeather.data.local.models.PWCity
import kishimotovn.pocketworksWeather.databinding.ItemSearchResultBinding
import kishimotovn.pocketworksWeather.features.search.viewmodels.SearchResultItemViewModel


class SearchResultItemDiffCallback: DiffUtil.ItemCallback<Pair<PWCity, String>>() {
    override fun areItemsTheSame(oldItem: Pair<PWCity, String>, newItem: Pair<PWCity, String>): Boolean {
        return oldItem.first.id == newItem.first.id
    }

    override fun areContentsTheSame(oldItem: Pair<PWCity, String>, newItem: Pair<PWCity, String>): Boolean {
        return oldItem.first.name == newItem.first.name
    }
}

class SearchResultListAdapter(val delegate: Delegate?): ListAdapter<Pair<PWCity, String>, SearchResultListAdapter.ViewHolder>(SearchResultItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = this.getItem(position)
        val onClickListener = View.OnClickListener {
            this.delegate?.didSelect(item.first, position)
        }
        holder.bind(item, onClickListener)
    }

    class ViewHolder(val binding: ItemSearchResultBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Pair<PWCity, String>, clickListener: View.OnClickListener) {
            with(this.binding) {
                this.viewModel = SearchResultItemViewModel(item.first, item.second)
                this.clickListener = clickListener
                this.executePendingBindings()
            }
        }
    }

    interface Delegate {
        fun didSelect(item: PWCity, index: Int)
    }
}