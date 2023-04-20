package com.villadevs.lemonadeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.villadevs.lemonadeapp.Constants
import com.villadevs.lemonadeapp.R
import com.villadevs.lemonadeapp.model.LemonTree

class LiminadeViewModel : ViewModel() {

    private var lemonSize = -1
    private var lemonTree = LemonTree()

    private val _lemonadeState = MutableLiveData("select")
    val lemonadeState: LiveData<String> = _lemonadeState

    private val _squeezeCount = MutableLiveData(-1)
    val squeezeCount: LiveData<Int> = _squeezeCount

    fun clickLemonImage() {
        when (_lemonadeState.value) {
            Constants.SELECT -> {
                lemonSize = lemonTree.pick()
                _squeezeCount.value = 0
                _lemonadeState.value = Constants.SQUEEZE
            }

            Constants.SQUEEZE -> {
                lemonSize--
                _squeezeCount.value = _squeezeCount.value?.plus(1)
                if (lemonSize == 0) {
                    _lemonadeState.value = Constants.DRINK
                    lemonSize = -1
                }
            }

            Constants.DRINK -> _lemonadeState.value = Constants.RESTART
            Constants.RESTART -> {
                _lemonadeState.value = Constants.SELECT
            }
        }
    }


}