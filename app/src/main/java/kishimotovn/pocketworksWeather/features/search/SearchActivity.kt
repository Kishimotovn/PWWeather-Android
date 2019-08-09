package kishimotovn.pocketworksWeather.features.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import kishimotovn.pocketworksWeather.base.BaseActivity
import kishimotovn.pocketworksWeather.data.local.models.PWCity
import kishimotovn.pocketworksWeather.databinding.ActivitySearchBinding
import kishimotovn.pocketworksWeather.features.search.adapter.SearchResultListAdapter
import kishimotovn.pocketworksWeather.features.shared.viewmodels.HeaderBarViewModel
import android.content.Intent
import android.graphics.Color
import kishimotovn.pocketworksWeather.R


class SearchActivity : BaseActivity(), SearchResultListAdapter.Delegate {
    lateinit var binding: ActivitySearchBinding

    private val viewModel by lazy {
        ViewModelProviders.of(this, this.viewModelFactory).get(SearchViewModel::class.java)
    }
    private val adapter = SearchResultListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        this.getComponent().inject(this)
        super.onCreate(savedInstanceState)
        this.binding = ActivitySearchBinding.inflate(LayoutInflater.from(this))
        this.setContentView(this.binding.root)
        this.initializeUI()
        this.bindUI()
    }

    override fun didSelect(item: PWCity, index: Int) {
        val resultIntent = Intent()
        resultIntent.putExtra(SEARCH_CITY_RESULT_INTENT_KEY, item)
        this.setResult(SEARCH_CITY_FINISH_RESULT_CODE, resultIntent)
        this.finish()
    }

    private fun bindUI() {
        this.viewModel.resultMessage.observe(this, Observer { resultMessage ->
            resultMessage.takeIf { !it.isEmpty() }?.let {
                this.binding.resultMessageTextView.text = it
                this.binding.resultMessageTextView.visibility = View.VISIBLE
            } ?: run {
                this.binding.resultMessageTextView.visibility = View.GONE
            }
        })

        this.viewModel.currentResults.observe(this, Observer { results ->
            Log.d("SearchActivity", "Found ${results.second.count()} results for term: ${results.first}")
            val formattedResults = results.second.map { city -> Pair(city, results.first) }
            this.adapter.submitList(formattedResults)
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
            this.headerBar.root.setBackgroundColor(Color.parseColor("#80000000"))
            this.headerBar.viewModel = HeaderBarViewModel("Enter city name", null)
            this.searchEditText.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    p0?.let {
                        this@SearchActivity.viewModel.searchCity(it.toString())
                    }
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            })
            this.resultRecycleView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.resultRecycleView.adapter = context.adapter
            this.executePendingBindings()
        }
    }

    override fun onBackPressed() {
        this.setResult(SEARCH_CITY_DISMISSED_RESULT_CODE)
        this.finish()
    }

    companion object {
        const val SEARCH_CITY_REQUEST_CODE = 110
        const val SEARCH_CITY_FINISH_RESULT_CODE = 111
        const val SEARCH_CITY_DISMISSED_RESULT_CODE = 112
        const val SEARCH_CITY_RESULT_INTENT_KEY = "city"
    }
}
