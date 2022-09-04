package com.hfad.smgrapp.navigator

import androidx.fragment.app.FragmentActivity
import com.hfad.smgrapp.view.Orv.BogieComponentsFragment
import com.hfad.smgrapp.view.Orv.OrvListFragment
import com.hfad.smgrapp.R
import com.hfad.smgrapp.view.Orv.AutomaticCouplerFragment
import com.hfad.smgrapp.view.Orv.BogieFragment
import com.hfad.smgrapp.view.Orv.BreakSystemFragment
import com.hfad.smgrapp.view.Orv.WheelsetFragment
import com.hfad.smgrapp.view.Orv.ComponentsFragment

class AppNavigatorImpl(private var fragmentActivity: FragmentActivity) : AppNavigator, AppNavigatorParam {
    override fun navigateTo(screen: Screen) {
        val fragment = when(screen) {
            Screen.ORV_LIST -> OrvListFragment()
            Screen.AUTOMATIC_COUPLER -> AutomaticCouplerFragment()
            Screen.BREAK_SYSTEM -> BreakSystemFragment()
            Screen.WHEELSET -> WheelsetFragment()
            Screen.BOGIE -> BogieFragment()
            Screen.COMPONENTS -> ComponentsFragment()
            //Screen.COMPONENTS_BOGIE_LIST -> BogieComponentsFragment()

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