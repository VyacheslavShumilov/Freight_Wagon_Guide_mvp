package com.hfad.smgrapp.navigator

enum class Screen {
    ORV_LIST,
    AUTOMATIC_COUPLER,
    BREAK_SYSTEM,
    WHEELSET,
    BOGIE,
    COMPONENTS,
    SAMPLES,
    SAMPLE_ABS,
    SAMPLE_VPG,
    SAMPLE_THICK,
}

interface AppNavigator {
    fun navigateTo(screen: Screen)
}

enum class ScreenParam {
    COMPONENTS_BOGIE_INFO
}

interface AppNavigatorParam {
    fun navigateToParam(screen: ScreenParam, modelBogie: String)
}

