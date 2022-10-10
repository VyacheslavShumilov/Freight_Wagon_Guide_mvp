package com.hfad.smgrapp.navigator

import androidx.fragment.app.FragmentActivity
import com.hfad.smgrapp.ui.orv.bogies_components.BogieComponentsFragment
import com.hfad.smgrapp.ui.orv.OrvListFragment
import com.hfad.smgrapp.R
import com.hfad.smgrapp.ui.orv.AutomaticCouplerFragment
import com.hfad.smgrapp.ui.orv.BogieFragment
import com.hfad.smgrapp.ui.orv.BreakSystemFragment
import com.hfad.smgrapp.ui.orv.WheelsetFragment
import com.hfad.smgrapp.ui.orv.bogies.BogiesListFragment

class AppNavigatorImpl(private var fragmentActivity: FragmentActivity) : AppNavigator, AppNavigatorParam {
    override fun navigateTo(screen: Screen) {
        val fragment = when(screen) {
            Screen.ORV_LIST -> OrvListFragment()
            Screen.AUTOMATIC_COUPLER -> AutomaticCouplerFragment()
            Screen.BREAK_SYSTEM -> BreakSystemFragment()
            Screen.WHEELSET -> WheelsetFragment()
            Screen.BOGIE -> BogieFragment()
            Screen.COMPONENTS -> BogiesListFragment()

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