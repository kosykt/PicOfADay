package ru.konstantin.material.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import kotlinx.android.synthetic.main.fragment_earth.*
import kotlinx.android.synthetic.main.main_fragment.*
import ru.konstantin.material.R
import ru.konstantin.material.databinding.FragmentEarthBinding
import ru.konstantin.material.ui.picture.PODServerResponseData

class EarthFragment : Fragment() {

    var _binding: FragmentEarthBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillFields()
    }

    private fun fillFields() {
        val photosAndDescriptions = arguments?.getParcelable<PODServerResponseData>(BUNDLE_EXTRA)
        binding.dayBeforeYeasterdayDescription.text = photosAndDescriptions?.explanation
        day_before_yeasterday_image_view.load(photosAndDescriptions?.url) {
            lifecycle(this@EarthFragment)
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)
        }
        println()
    }

    companion object {
        const val BUNDLE_EXTRA = "descriptionPlanet"

        fun newInstance(bundle: Bundle): EarthFragment {
            val fragment = EarthFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}