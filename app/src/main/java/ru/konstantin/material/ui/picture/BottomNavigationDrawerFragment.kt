package ru.konstantin.material.ui.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_navigation_layout.*
import ru.konstantin.material.R
import ru.konstantin.material.databinding.BottomNavigationLayoutBinding
import ru.konstantin.material.ui.fragments.EarthFragment
import ru.konstantin.material.ui.fragments.FavoriteItemsFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> {
                    Toast.makeText(context, "1", Toast.LENGTH_SHORT).show()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment, FavoriteItemsFragment.newInstance())
                        ?.commitAllowingStateLoss()
                }
                R.id.navigation_two -> Toast.makeText(context, "2", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
