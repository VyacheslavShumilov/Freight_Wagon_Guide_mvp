package com.hfad.smgrapp.ui.smgr

// REDESIGN — WagonActivity.kt
// Changes vs original:
//   - Removed: TabLayout, TabLayoutMediator, DotsIndicator
//   - Added: BottomNavigationView connected bidirectionally to ViewPager2
//   - Page swipe updates bottom nav selection
//   - Bottom nav tap changes ViewPager2 page

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hfad.smgrapp.R
import com.hfad.smgrapp.ui.smgr.adapter_view_pager.ViewPagerAdapter
import com.hfad.smgrapp.databinding.ActivityWagonBinding
import com.hfad.smgrapp.model.Wagons

class WagonActivity : FragmentActivity() {

    private lateinit var binding: ActivityWagonBinding
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNav: BottomNavigationView

    // Map ViewPager position → menu item ID
    private val pageToNavId = mapOf(
        0 to R.id.nav_params,
        1 to R.id.nav_repairs,
        2 to R.id.nav_cargos
    )
    private val navIdToPage = pageToNavId.entries.associate { (k, v) -> v to k }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWagonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wagons = intent.getSerializableExtra("WAGON") as Wagons

        adapter   = ViewPagerAdapter(wagons, this)
        viewPager = binding.viewPager2
        bottomNav = binding.bottomNav

        viewPager.adapter = adapter

        // Disable swipe (bottom nav is the navigation mechanism)
        // Comment out this line to re-enable swipe if desired:
        viewPager.isUserInputEnabled = false

        // ── Bottom nav → ViewPager ────────────────────────────────────────────
        bottomNav.setOnItemSelectedListener { item ->
            val page = navIdToPage[item.itemId] ?: return@setOnItemSelectedListener false
            viewPager.setCurrentItem(page, true)
            true
        }

        // ── ViewPager → Bottom nav (sync on swipe if isUserInputEnabled = true) ──
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val id = pageToNavId[position] ?: return
                bottomNav.selectedItemId = id
            }
        })
    }
}
