package com.hfad.smgrapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.hfad.smgrapp.model.Wagons
import com.hfad.smgrapp.view.smgr.CargosWagonFragment
import com.hfad.smgrapp.view.smgr.ParameterWagonFragment
import com.hfad.smgrapp.view.smgr.RepairsWagonFragment

private const val PARAMETER_WAGON_FRAGMENT = 0
private const val REPAIRS_WAGON_FRAGMENT = 1
private const val CARGOS_WAGON_FRAGMENT = 2

class ViewPagerAdapter(var wagons: Wagons, fragmentManager: FragmentManager): FragmentPagerAdapter (fragmentManager){

    private val fragments = arrayOf(ParameterWagonFragment(wagons), RepairsWagonFragment(wagons), CargosWagonFragment(wagons))

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> fragments[PARAMETER_WAGON_FRAGMENT]
            1 -> fragments[REPAIRS_WAGON_FRAGMENT]
            2 -> fragments[CARGOS_WAGON_FRAGMENT]
            else -> fragments[PARAMETER_WAGON_FRAGMENT]
        }
    }

    override fun getCount(): Int{
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position) {
            0 -> "Параметры"
            1 -> "Ремонты"
            2 -> "Грузы"
            else -> "Параметры"
        }
    }
}