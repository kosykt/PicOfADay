package ru.konstantin.material.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.api.load
import kotlinx.android.synthetic.main.fragment_earth.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.*
import ru.konstantin.material.R
import ru.konstantin.material.databinding.FragmentEarthBinding
import ru.konstantin.material.ui.picture.PODServerResponseData

class EarthFragment : Fragment() {

    var textIsVisible = false

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

        scaleImageAndBack()
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

    fun scaleImageAndBack(){
        day_before_yeasterday_image_view.setOnClickListener {
            textIsVisible = !textIsVisible
            TransitionManager.beginDelayedTransition(
                binding.coordinatorLayout, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )

            val params = day_before_yeasterday_image_view.layoutParams
            params.height = if (textIsVisible) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT

            day_before_yeasterday_image_view.layoutParams = params

            day_before_yeasterday_image_view.scaleType = if (textIsVisible) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        }
    }
}