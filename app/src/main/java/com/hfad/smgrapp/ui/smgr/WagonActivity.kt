package com.hfad.smgrapp.ui.smgr


import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hfad.smgrapp.R
import com.hfad.smgrapp.ui.smgr.adapter_view_pager.ViewPagerAdapter
import com.hfad.smgrapp.databinding.ActivityWagonBinding
import com.hfad.smgrapp.model.Wagons


class WagonActivity : FragmentActivity() {

    private lateinit var binding: ActivityWagonBinding
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout


    private val tabCustom: ArrayList<Int> = arrayListOf(
        R.layout.custom_tab_parameters,
        R.layout.custom_tab_repair,
        R.layout.custom_tab_cargos,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWagonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wagons = intent.getSerializableExtra("WAGON") as Wagons

        adapter = ViewPagerAdapter(wagons, this)
        viewPager = binding.viewPager2
        viewPager.adapter = adapter

        tabLayout = binding.tabLayout

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.customView = layoutInflater.inflate(tabCustom[position], null)
        }.attach()

        val dotsIndicator = binding.dotsIndicator
        dotsIndicator.attachTo(viewPager)
    }
}