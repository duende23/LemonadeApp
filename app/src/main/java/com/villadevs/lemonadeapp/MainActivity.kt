package com.villadevs.lemonadeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.villadevs.lemonadeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //private val viewmodel: DiceViewmodel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private var lemonadeState = "select"

    // Default lemonSize to -1
    private var lemonSize = -1

    // Default the squeezeCount to -1
    private var squeezeCount = -1

    private var lemonTree = LemonTree()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_main)


        // === DO NOT ALTER THE CODE IN THE FOLLOWING IF STATEMENT ===
        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(Constants.LEMONADE_STATE, "select")
            lemonSize = savedInstanceState.getInt(Constants.LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(Constants.SQUEEZE_COUNT, -1)
        }
        // === END IF STATEMENT ===

        setViewElements()
        binding.ivLemonState.setOnClickListener { clickLemonImage() }
        binding.ivLemonState.setOnLongClickListener { showSnackbar() }
    }

    /**
     * === DO NOT ALTER THIS METHOD ===
     *
     * This method saves the state of the app if it is put in the background.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(Constants.LEMONADE_STATE, lemonadeState)
        outState.putInt(Constants.LEMON_SIZE, lemonSize)
        outState.putInt(Constants.SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }

    /**
     * Clicking will elicit a different response depending on the state.
     * This method determines the state and proceeds with the correct action.
     */
    private fun clickLemonImage() {
        when (lemonadeState) {
            Constants.SELECT -> {
                lemonSize = lemonTree.pick()
                squeezeCount = 0
                lemonadeState = Constants.SQUEEZE
            }

            Constants.SQUEEZE -> {
                lemonSize--
                squeezeCount++
                if (lemonSize == 0) {
                    lemonadeState = Constants.DRINK
                    lemonSize = -1
                }

            }

            Constants.DRINK -> lemonadeState = Constants.RESTART
            Constants.RESTART -> {
                lemonadeState = Constants.SELECT
            }
        }
        setViewElements()
    }

    /**
     * Set up the view elements according to the state.
     */
    private fun setViewElements() {
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

    /**
     * === DO NOT ALTER THIS METHOD ===
     *
     * Long clicking the lemon image will show how many times the lemon has been squeezed.
     */

    private fun showSnackbar(): Boolean {
        if (lemonadeState != Constants.SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar.make(binding.root, squeezeText, Snackbar.LENGTH_SHORT).show()
        return true
    }

}

/**
 * A Lemon tree class with a method to "pick" a lemon. The "size" of the lemon is randomized
 * and determines how many times a lemon needs to be squeezed before you get lemonade.
 */
class LemonTree {
    fun pick(): Int {
        return (2..4).random()
    }
}
