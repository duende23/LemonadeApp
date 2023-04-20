package com.villadevs.lemonadeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.villadevs.lemonadeapp.databinding.ActivityMainBinding
import com.villadevs.lemonadeapp.viewmodel.LiminadeViewModel

class MainActivity : AppCompatActivity() {

    private val viewmodel: LiminadeViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_main)

        binding.ivLemonState.setOnClickListener { viewmodel.clickLemonImage() }

        viewmodel.lemonadeState.observe(this) { lemonadeState ->
            setViewElements(lemonadeState)
        }

        binding.ivLemonState.setOnLongClickListener { showSnackbar(viewmodel.lemonadeState.value!!, viewmodel.squeezeCount.value!!)}
    }

    /**
     * Set up the view elements according to the state.
     */
    private fun setViewElements(lemonadeState:String) {
        val drawableLemonade = when (lemonadeState) {
            Constants.SELECT -> R.drawable.lemon_tree
            Constants.SQUEEZE -> R.drawable.lemon_squeeze
            Constants.DRINK -> R.drawable.lemon_drink
            else -> R.drawable.lemon_restart
        }

        val textLemonade = when (lemonadeState) {
            Constants.SELECT -> R.string.lemon_select
            Constants.SQUEEZE -> R.string.lemon_squeeze
            Constants.DRINK -> R.string.lemon_drink
            else -> R.string.lemon_empty_glass
        }

        binding.ivLemonState.setImageResource(drawableLemonade)
        binding.tvAction.text = getString(textLemonade)

    }

    private fun showSnackbar(lemonadeState: String, squeezeCount: Int): Boolean {
        if (lemonadeState != Constants.SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar.make(binding.root, squeezeText, Snackbar.LENGTH_SHORT).show()
        return true
    }

}

