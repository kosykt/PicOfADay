package ru.konstantin.material.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_bottom_navigation.*
import ru.konstantin.material.R
import ru.konstantin.material.databinding.FragmentBottomNavigationBinding
import ru.konstantin.material.model.Mapping
import ru.konstantin.material.model.ViewState
import ru.konstantin.material.ui.picture.PODServerResponseData
import ru.konstantin.material.ui.viewModel.PODListViewModel
import java.time.LocalDate


class BottomNavigationFragment : AppCompatActivity() {
    private var _binding: FragmentBottomNavigationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PODListViewModel by lazy {
        ViewModelProviders.of(this).get(PODListViewModel::class.java)
    }

    lateinit var bundle: Bundle

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTheme(THEME_ID)
        viewModel.getData(LocalDate.now().minusDays(2).toString(), LocalDate.now().toString())
            .observe(this, Observer { renderData(it) })
    }

    private fun renderData(data: ViewState) {
        when (data) {
            is ViewState.Success<*> -> {
                bottom_navigation_view.visibility = View.VISIBLE
                loadinglayout_in_bottom_navigatio_fragment.visibility = View.GONE
                if ((data.stateData as List<PODServerResponseData>).isNullOrEmpty()) {
//                    toast("Link is empty")
                } else {
                    var photos = data.stateData as List<PODServerResponseData>
                    println(photos)
                    bundle = Bundle()
                    binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                        when (item.itemId) {
                            R.id.bottom_view_day_before -> {

                                bundle.putParcelable(EarthFragment.BUNDLE_EXTRA, Mapping().convertPODServerResponseDataToItem(photos[0]))
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.activity_api_bottom_container, EarthFragment.newInstance(bundle))
                                    .commitAllowingStateLoss()
                                true
                            }
                            R.id.bottom_view_yesterday -> {
                                bundle.putParcelable(EarthFragment.BUNDLE_EXTRA, Mapping().convertPODServerResponseDataToItem(photos[1]))
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.activity_api_bottom_container, EarthFragment.newInstance(bundle))
                                    .commitAllowingStateLoss()
                                true
                            }
                            R.id.bottom_view_today -> {
                                bundle.putParcelable(EarthFragment.BUNDLE_EXTRA, Mapping().convertPODServerResponseDataToItem(photos[2]))
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.activity_api_bottom_container, EarthFragment.newInstance(bundle))
                                    .commitAllowingStateLoss()
                                true
                            }
                            else -> {
                                bundle.putParcelable(EarthFragment.BUNDLE_EXTRA, Mapping().convertPODServerResponseDataToItem(photos[0]))
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.activity_api_bottom_container, EarthFragment.newInstance(bundle))
                                    .commitAllowingStateLoss()
                                true
                            }
                        }
                    }
                    bottom_navigation_view.selectedItemId = R.id.bottom_view_day_before
                    bottom_navigation_view.getOrCreateBadge(R.id.bottom_view_day_before)
                }
            }
            is ViewState.Loading -> {
                if (loadinglayout_in_bottom_navigatio_fragment.visibility != View.VISIBLE) {
                    loadinglayout_in_bottom_navigatio_fragment.visibility = View.VISIBLE
                }
            }
            is ViewState.Error -> {
                loadinglayout_in_bottom_navigatio_fragment.visibility = View.GONE

            }
        }
    }
}