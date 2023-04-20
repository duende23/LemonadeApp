package com.villadevs.lemonadeapp

object Constants {
    val LEMONADE_STATE = "LEMONADE_STATE"
    val LEMON_SIZE = "LEMON_SIZE"
    val SQUEEZE_COUNT = "SQUEEZE_COUNT"

    // SELECT represents the "pick lemon" state
    val SELECT = "select"

    // SQUEEZE represents the "squeeze lemon" state
    val SQUEEZE = "squeeze"

    // DRINK represents the "drink lemonade" state
    val DRINK = "drink"

    // RESTART represents the state where the lemonade has been drunk and the glass is empty
    val RESTART = "restart"
}