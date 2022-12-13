package com.hfad.smgrapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.hfad.smgrapp.R
import com.hfad.smgrapp.ui.orv.OrvActivity
import com.hfad.smgrapp.ui.smgr.wagons.WagonsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_start)

        val constraintsContainer: ConstraintLayout = findViewById(R.id.container)

        var isDetailsShown = false

        fun showDetails() {
            isDetailsShown = true

            val constraints = ConstraintSet().apply {
                clone(this@MainActivity, R.layout.activity_main_end)
            }

            ChangeBounds().apply {
                interpolator = AnticipateOvershootInterpolator(1.0f).apply {
                    duration = 2000L
                }
            }.also {
                TransitionManager.beginDelayedTransition(constraintsContainer, it)
            }

            constraints.applyTo(constraintsContainer)
        }

        fun hideDetails() {
            isDetailsShown = false


            val constraints = ConstraintSet().apply {
                clone(this@MainActivity, R.layout.activity_main_start)
            }

            ChangeBounds().apply {
                interpolator = AnticipateOvershootInterpolator(1.0f).apply {
                    duration = 2000L
                }
            }.also {
                TransitionManager.beginDelayedTransition(constraintsContainer, it)
            }

            constraints.applyTo(constraintsContainer)
        }

        findViewById<ImageView>(R.id.titleImage).setOnClickListener {
            if (isDetailsShown) {
                hideDetails()
            } else {
                showDetails()

            }
        }

        findViewById<Button>(R.id.btnToSmgr).setOnClickListener {
            val intent = Intent(this, WagonsActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnToOrv).setOnClickListener {

            val intent = Intent(this, OrvActivity::class.java)
            startActivity(intent)
        }
    }
}