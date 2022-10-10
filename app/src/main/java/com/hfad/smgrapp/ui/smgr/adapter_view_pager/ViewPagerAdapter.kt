package com.hfad.smgrapp.ui.smgr.adapter_view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hfad.smgrapp.model.Wagons
import com.hfad.smgrapp.ui.smgr.wagon_cargos.CargosWagonFragment
import com.hfad.smgrapp.ui.smgr.ParameterWagonFragment
import com.hfad.smgrapp.ui.smgr.RepairsWagonFragment

private const val PARAMETER_WAGON_FRAGMENT = 0
private const val REPAIRS_WAGON_FRAGMENT = 1
private const val CARGOS_WAGON_FRAGMENT = 2

class ViewPagerAdapter(var wagons: Wagons, fragment: FragmentActivity): FragmentStateAdapter (fragment){

    private val fragments = arrayOf(ParameterWagonFragment(wagons), RepairsWagonFragment(wagons), CargosWagonFragment(wagons))

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> fragments[PARAMETER_WAGON_FRAGMENT]
            1 -> fragments[REPAIRS_WAGON_FRAGMENT]
            2 -> fragments[CARGOS_WAGON_FRAGMENT]
            else -> fragments[PARAMETER_WAGON_FRAGMENT]
        }
    }
}