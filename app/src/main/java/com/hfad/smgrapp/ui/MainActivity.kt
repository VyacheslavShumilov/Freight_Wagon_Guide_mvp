package com.hfad.smgrapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.google.android.material.button.MaterialButton
import com.hfad.smgrapp.R
import com.hfad.smgrapp.ui.orv.OrvActivity
import com.hfad.smgrapp.ui.smgr.wagons.WagonsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var container: ConstraintLayout
    private var isExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Стартуем с compact-макета (только поезд + заголовок + подсказка).
        // По тапу развернётся до полного activity_main_end.
        setContentView(R.layout.activity_main_start)

        container = findViewById(R.id.container)

        // Тап по любой части экрана (кроме кнопок) раскрывает макет.
        container.setOnClickListener {
            if (!isExpanded) expandLayout()
        }

        findViewById<MaterialButton>(R.id.btnToSmgr).setOnClickListener {
            startActivity(Intent(this, WagonsActivity::class.java))
        }

        findViewById<MaterialButton>(R.id.btnToOrv).setOnClickListener {
            startActivity(Intent(this, OrvActivity::class.java))
        }

        findViewById<MaterialButton>(R.id.btnCheckUpdate).setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.hfad.smgrapp")
                )
            )
        }
    }

    /**
     * RESTORED — анимация раскрытия макета при тапе.
     *
     * ConstraintSet.clone(this, R.layout.activity_main_end) читает
     * constraints целевого макета и применяет их к текущему контейнеру.
     * TransitionManager анимирует ВСЕ изменения constraints одновременно:
     *   • поезд и дым уезжают на позицию из activity_main_end;
     *   • текстовые блоки и кнопки плавно появляются благодаря Fade.
     *
     * AnticipateOvershootInterpolator — характерная "пружинистая" кривая,
     * которая была в старой версии: сначала лёгкий откат назад, потом
     * движение с перелётом и возврат. Даёт ощущение веса и инерции —
     * уместно для поезда.
     */
    private fun expandLayout() {
        isExpanded = true

        val endConstraints = ConstraintSet().apply {
            clone(this@MainActivity, R.layout.activity_main_end)
        }

        val transition = TransitionSet().apply {
            addTransition(ChangeBounds().apply {
                interpolator = AnticipateOvershootInterpolator(1.0f)
            })
            addTransition(Fade(Fade.IN))
            duration = 900L
        }

        TransitionManager.beginDelayedTransition(container, transition)
        endConstraints.applyTo(container)
    }
}