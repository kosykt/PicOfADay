package ru.konstantin.material.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*
import ru.konstantin.material.R
import ru.konstantin.material.databinding.MainFragmentBinding
import ru.konstantin.material.model.Item
import ru.konstantin.material.model.ViewState
import ru.konstantin.material.ui.MainActivity
import ru.konstantin.material.ui.picture.BottomNavigationDrawerFragment
import ru.konstantin.material.ui.picture.PODServerResponseData
import ru.konstantin.material.ui.viewModel.ItemListViewModel
import ru.konstantin.material.ui.viewModel.PictureOfTheDayViewModel
import java.util.*

var THEME_ID = 0
var BUTTON_ID = 0

class PictureOfTheDayFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProviders.of(this).get(PictureOfTheDayViewModel::class.java)
    }

    lateinit var favItem: Item

    private val itemListViewModel: ItemListViewModel by lazy {
        ViewModelProvider(this).get(ItemListViewModel::class.java)
    }

//    val repository: ItemRepository = ItemRepositoryImpl()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.setTheme(THEME_ID)
        viewModel.getData()
            .observe(viewLifecycleOwner, Observer { renderData(it) })
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(
            binding.bottomSheetContainer.root,
            BottomSheetBehavior.STATE_COLLAPSED
        )
        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }
        setBottomAppBar(view)

        binding.imageView.setOnClickListener {
            hideOrShowBottomSheet()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.app_bar_save_item -> { /*repository.addItem(Item(0,"wsd", "Desc", Date(), "https://...."))*/ println(
                "SAVE..."
            )
                itemListViewModel.saveItem(favItem)
            }
            R.id.app_bar_view_3_days -> activity?.let {
                startActivity(
                    Intent(
                        it,
                        BottomNavigationFragment::class.java
                    )
                )
            }
            R.id.app_bar_settings -> {
                val bundle = Bundle()
                bundle.putInt(SettingPropertiesFragment.BUNDLE_THEME_ID, THEME_ID)
                bundle.putInt(SettingPropertiesFragment.BUNDLE_BUTTON_ID, BUTTON_ID)
                activity
                    ?.supportFragmentManager?.beginTransaction()
                    ?.replace(
                        R.id.container, SettingPropertiesFragment.newInstance(
                            Bundle(bundle)
                        )
                    )
                    ?.addToBackStack(null)
                    ?.commit()
            }
            R.id.app_bar_fav -> {
                activity
                    ?.supportFragmentManager?.beginTransaction()
                    ?.replace(
                        R.id.container, FavoriteItemsFragment.newInstance()
                    )
                    ?.addToBackStack(null)
                    ?.commit()
            }

            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(data: ViewState) {
        when (data) {
            is ViewState.Success<*> -> {
                main.visibility = View.VISIBLE
                loadinglayout_in_main.visibility = View.GONE
                if ((data.stateData as PODServerResponseData).url.isNullOrEmpty()) {
                    toast("Link is empty")
                } else {
                    favItem = Item(0,
                        data.stateData.title?:"",
                        data.stateData.explanation,
                        Date(),
                        data.stateData.url
                        )
                    binding.imageDate.text = "Photo date: ${(data.stateData).date}"

                    binding.bottomSheetContainer.bottomSheetDescriptionHeader.text =
                        (data.stateData).title
                    binding.bottomSheetContainer.bottomSheetDescription.text =
                        (data.stateData).explanation
//                    setBottomSheetBehavior(binding.bottomSheetContainer.root, BottomSheetBehavior.STATE_HALF_EXPANDED)

                    image_view.load((data.stateData).url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is ViewState.Loading -> {
                if (loadinglayout_in_main.visibility != View.VISIBLE) {
                    loadinglayout_in_main.visibility = View.VISIBLE
                }
            }
            is ViewState.Error -> {
                loadinglayout_in_main.visibility = View.GONE
                Snackbar.make(
                    binding.main,
                    resources.getString(R.string.error_text),
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(resources.getString(R.string.reload_text)) {
                        viewModel.getData()
                    }.show()

                toast(data.error.message)
            }
        }
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        fab.setOnClickListener {
            if (isMain) {
                isMain = false
                bottom_app_bar.navigationIcon = null
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                bottom_app_bar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout, state: Int) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = state
    }

    private fun hideOrShowBottomSheet() {
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_COLLAPSED) {
            setBottomSheetBehavior(
                binding.bottomSheetContainer.root,
                BottomSheetBehavior.STATE_COLLAPSED
            )
        } else {
            setBottomSheetBehavior(
                binding.bottomSheetContainer.root,
                BottomSheetBehavior.STATE_HALF_EXPANDED
            )
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}

interface OnItemViewClickListener {
    fun onItemViewClick(item: Item)
}
