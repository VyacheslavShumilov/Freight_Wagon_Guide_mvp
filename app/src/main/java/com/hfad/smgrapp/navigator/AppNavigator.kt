package com.hfad.smgrapp.navigator

enum class Screen {
    ORV_LIST,
    AUTOMATIC_COUPLER,
    BREAK_SYSTEM,
    WHEELSET,
    BOGIE,
    COMPONENTS
}

interface AppNavigator {
    fun navigateTo(screen: Screen)
}
