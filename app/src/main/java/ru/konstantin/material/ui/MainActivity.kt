package ru.konstantin.material.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.konstantin.material.databinding.MainActivityBinding
import ru.konstantin.material.ui.picture.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    private var _binding: MainActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
