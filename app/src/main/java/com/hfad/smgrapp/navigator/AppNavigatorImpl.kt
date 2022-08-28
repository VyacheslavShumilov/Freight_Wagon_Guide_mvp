package com.hfad.smgrapp.navigator

import androidx.fragment.app.FragmentActivity
import com.hfad.smgrapp.OrvListFragment
import com.hfad.smgrapp.R
import com.hfad.smgrapp.view.*

class AppNavigatorImpl(private var fragmentActivity: FragmentActivity) : AppNavigator {
    override fun navigateTo(screen: Screen) {
        val fragment = when(screen) {
            Screen.ORV_LIST -> OrvListFragment()
            Screen.AUTOMATIC_COUPLER -> AutomaticCouplerFragment()
            Screen.BREAK_SYSTEM -> BreakSystemFragment()
            Screen.WHEELSET -> WheelsetFragment()
            Screen.BOGIE -> BogieFragment()
            Screen.COMPONENTS -> ComponentsFragment()
        }

        fragmentActivity.supportFragmentManager.beginTransaction()
            .replace(R.id.orvListContainer, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }
}