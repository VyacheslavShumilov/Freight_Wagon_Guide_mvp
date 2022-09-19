package com.hfad.smgrapp.view.smgr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.hfad.smgrapp.R
import com.hfad.smgrapp.adapter.ViewPagerAdapter
import com.hfad.smgrapp.databinding.ActivityWagonBinding
import com.hfad.smgrapp.model.Wagons

private const val PARAMETERS = 0
private const val REPAIRS = 1
private const val CARGOS = 2

class WagonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWagonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWagonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wagons = intent.getSerializableExtra("WAGON") as? Wagons

        with(binding) {
            viewPager.adapter = wagons?.let { ViewPagerAdapter(it, supportFragmentManager) }
            tabLayout.setupWithViewPager(binding.viewPager)
            setHighlightedTab(PARAMETERS)

            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                override fun onPageSelected(position: Int) {
                    setHighlightedTab(position)
                }

                override fun onPageScrollStateChanged(state: Int) {}

                override fun onPageScrolled(
                    position: Int, positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }
            })
        }

    }

    private fun setHighlightedTab(position: Int) {
        val layoutInflater = LayoutInflater.from(this@WagonActivity)
        with(binding) {
            tabLayout.getTabAt(PARAMETERS)?.customView = null
            tabLayout.getTabAt(REPAIRS)?.customView = null
            tabLayout.getTabAt(CARGOS)?.customView = null
        }

        when (position) {
            PARAMETERS -> {
                setParametersHighlightedTab(layoutInflater)
            }
            REPAIRS -> {
                setRepairsHighlightedTab(layoutInflater)
            }
            CARGOS -> {
                setCargosHighlightedTab(layoutInflater)
            }
            else -> {
                setParametersHighlightedTab(layoutInflater)
            }
        }
    }


    private fun setParametersHighlightedTab(layoutInflater: LayoutInflater?) {
        val parameters = layoutInflater?.inflate(R.layout.custom_tab_parameters, null)
        parameters?.findViewById<AppCompatTextView>(R.id.tab_image_textview)?.setTextColor(ContextCompat.getColor(this, R.color.custom_tab_clicked))
        binding.tabLayout.getTabAt(PARAMETERS)?.customView = parameters
        binding.tabLayout.getTabAt(REPAIRS)?.customView = layoutInflater?.inflate(R.layout.custom_tab_repair, null)
        binding.tabLayout.getTabAt(CARGOS)?.customView = layoutInflater?.inflate(R.layout.custom_tab_cargos, null)
    }

    private fun setRepairsHighlightedTab(layoutInflater: LayoutInflater?) {
        val repairs = layoutInflater?.inflate(R.layout.custom_tab_repair, null)
        repairs?.findViewById<AppCompatTextView>(R.id.tab_image_textview)?.setTextColor(ContextCompat.getColor(this, R.color.custom_tab_clicked))
        binding.tabLayout.getTabAt(REPAIRS)?.customView = repairs
        binding.tabLayout.getTabAt(PARAMETERS)?.customView = layoutInflater?.inflate(R.layout.custom_tab_parameters, null)
        binding.tabLayout.getTabAt(CARGOS)?.customView = layoutInflater?.inflate(R.layout.custom_tab_cargos, null)
    }

    private fun setCargosHighlightedTab(layoutInflater: LayoutInflater?) {
        val cargos = layoutInflater?.inflate(R.layout.custom_tab_cargos, null)
        cargos?.findViewById<AppCompatTextView>(R.id.tab_image_textview)?.setTextColor(ContextCompat.getColor(this, R.color.custom_tab_clicked))
        binding.tabLayout.getTabAt(CARGOS)?.customView = cargos
        binding.tabLayout.getTabAt(PARAMETERS)?.customView = layoutInflater?.inflate(R.layout.custom_tab_parameters, null)
        binding.tabLayout.getTabAt(REPAIRS)?.customView = layoutInflater?.inflate(R.layout.custom_tab_repair, null)
    }
}