package ru.konstantin.material.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.konstantin.material.R
import ru.konstantin.material.adapters.ItemAdapter
import ru.konstantin.material.databinding.FragmentFavoriteItemsBinding
import ru.konstantin.material.model.Item
import ru.konstantin.material.model.ViewState
import ru.konstantin.material.ui.hide
import ru.konstantin.material.ui.show
import ru.konstantin.material.ui.showSnackBar
import ru.konstantin.material.ui.viewModel.ItemListViewModel

class FavoriteItemsFragment : Fragment() {

    private var _binding: FragmentFavoriteItemsBinding? = null
    private val binding get() = _binding!!
    private val itemListViewModel: ItemListViewModel by lazy {
        ViewModelProvider(this).get(ItemListViewModel::class.java)
    }

    private val adapter: ItemAdapter by lazy {
        ItemAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter.setOnItemViewClickListener(object: OnItemViewClickListener {
            override fun onItemViewClick(item: Item) {
                val manager = activity?.supportFragmentManager
                if (manager != null) {
                    val bundle = Bundle()
                    bundle.putParcelable(EarthFragment.BUNDLE_EXTRA, item)
                    manager.beginTransaction()
                        .add(R.id.container, EarthFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commit()
                }
            }
        })
        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
        }
        itemListViewModel.getData().observe(viewLifecycleOwner) { renderItemListData(it) }
        itemListViewModel.getAllItems()
    }


    private fun renderItemListData(viewState: ViewState) {
        when (viewState) {
            is ViewState.Success<*> -> {
                binding.mainFragmentRecyclerView.show()
                binding.loadingLayout.hide()
                adapter.setData(viewState.stateData as List<Item>)
            }
            is ViewState.Loading -> {
                binding.mainFragmentRecyclerView.hide()
                binding.loadingLayout.show()
            }
            is ViewState.Error -> {
                binding.mainFragmentRecyclerView.show()
                binding.loadingLayout.hide()
                binding.mainFragmentRecyclerView.showSnackBar(
                    getString(R.string.error_text),
                    getString(R.string.reload_text)
                ) {
                    itemListViewModel.getAllItems()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = FavoriteItemsFragment()
    }
}