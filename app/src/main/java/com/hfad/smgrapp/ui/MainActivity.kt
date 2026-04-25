package com.hfad.smgrapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.AnticipateOvershootInterpolator
import androidx.activity.result.contract.ActivityResultContracts
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
import com.hfad.smgrapp.update.UpdateManager

class MainActivity : AppCompatActivity() {

    private lateinit var container: ConstraintLayout
    private var isExpanded = false

    private lateinit var updateManager: UpdateManager

    /**
     * Launcher для In-App Updates. Регистрируется на этапе создания поля
     * (до super.onCreate), потому что AndroidX требует регистрацию
     * до вступления Activity в STARTED state.
     */
    private val updateLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            updateManager.handleUpdateResult(result.resultCode)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_start)

        container = findViewById(R.id.container)

        // ── In-App Updates ─────────────────────────────────────────
        updateManager = UpdateManager(this, updateLauncher)
        updateManager.checkForUpdate()

        // ── Тап по экрану раскрывает макет ──────────────────────────
        container.setOnClickListener {
            if (!isExpanded) expandLayout()
        }

        findViewById<MaterialButton>(R.id.btnToSmgr).setOnClickListener {
            startActivity(Intent(this, WagonsActivity::class.java))
        }

        findViewById<MaterialButton>(R.id.btnToOrv).setOnClickListener {
            startActivity(Intent(this, OrvActivity::class.java))
        }

        // CHANGED — кнопка "Обновить приложение" больше не нужна,
        // обновление теперь через In-App Updates. Если в layout
        // btnCheckUpdate ещё присутствует — её можно удалить из XML.
    }

    override fun onResume() {
        super.onResume()
        if (::updateManager.isInitialized) {
            updateManager.checkUpdateInProgress()
        }
    }

    override fun onDestroy() {
        if (::updateManager.isInitialized) {
            updateManager.unregisterListener()
        }
        super.onDestroy()
    }

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