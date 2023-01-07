package com.hfad.smgrapp.navigator

import androidx.fragment.app.FragmentActivity
import com.hfad.smgrapp.ui.orv.bogies_components.BogieComponentsFragment
import com.hfad.smgrapp.R
import com.hfad.smgrapp.ui.orv.*
import com.hfad.smgrapp.ui.orv.bogies.BogiesListFragment
import com.hfad.smgrapp.ui.orv.samples.*

class AppNavigatorImpl(private var fragmentActivity: FragmentActivity) : AppNavigator, AppNavigatorParam {
    override fun navigateTo(screen: Screen) {
        val fragment = when(screen) {
            Screen.ORV_LIST -> OrvListFragment()
            Screen.AUTOMATIC_COUPLER -> AutomaticCouplerFragment()
            Screen.BREAK_SYSTEM -> BreakSystemFragment()
            Screen.WHEELSET -> WheelsetFragment()
            Screen.BOGIE -> BogieFragment()
            Screen.COMPONENTS -> BogiesListFragment()
            Screen.SAMPLES -> SamplesFragment()
            Screen.SAMPLE_ABS -> SampleAbsFragment()
            Screen.SAMPLE_VPG -> SampleVpgFragment()
            Screen.SAMPLE_THICK -> SampleThickFragment()
            Screen.SAMPLE_COMB -> SampleCombFragment()
            Screen.SAMPLE_BRACE -> SampleBraceFragment()
            Screen.SAMPLE_PROF -> SampleProfFragment()

        }

        fragmentActivity.supportFragmentManager.beginTransaction()
            .replace(R.id.orvListContainer, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }

    override fun navigateToParam(screen: ScreenParam, modelBogie: String) {
        val fragment = when(screen) {
            ScreenParam.COMPONENTS_BOGIE_INFO -> BogieComponentsFragment(modelBogie)
        }

        fragmentActivity.supportFragmentManager.beginTransaction()
            .replace(R.id.orvListContainer, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }
}