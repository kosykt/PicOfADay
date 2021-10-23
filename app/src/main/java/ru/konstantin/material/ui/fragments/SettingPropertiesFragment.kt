package ru.konstantin.material.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.konstantin.material.R
import ru.konstantin.material.databinding.FragmentSettingPropertiesBinding


class SettingPropertiesFragment : Fragment() {
    private var _binding: FragmentSettingPropertiesBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val BUNDLE_THEME_ID = "theme_id"
        const val BUNDLE_BUTTON_ID = "button_id"

        fun newInstance(bundle: Bundle): SettingPropertiesFragment {
            val fragment = SettingPropertiesFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingPropertiesBinding.inflate(inflater, container, false)
        arguments?.getInt(BUNDLE_THEME_ID)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity.let {
            val radioGroup = binding.radioGroup
            if (BUTTON_ID != 0) {
                radioGroup.check(BUTTON_ID)
            }

            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                if (checkedId != BUTTON_ID) {
                    when {
                        binding.radioButton1.id == checkedId -> {
                                THEME_ID = R.style.Theme_MyTheme

                                it?.setTheme(R.style.Theme_MyTheme)
                                it?.recreate()
                                println("Загрузили тему 1")
                        }

                        binding.radioButton2.id == checkedId -> {
                                THEME_ID = R.style.Theme_MyTheme_OtherSide

                                it?.setTheme(R.style.Theme_MyTheme_OtherSide)
                                it?.recreate()
                                println("Загрузили тему 2")
                        }
                    }
                }
                BUTTON_ID = checkedId
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}