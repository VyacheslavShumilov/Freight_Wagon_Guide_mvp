package com.hfad.smgrapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.material.button.MaterialButton
import com.hfad.smgrapp.BuildConfig
import com.hfad.smgrapp.R
import com.hfad.smgrapp.ui.orv.OrvActivity
import com.hfad.smgrapp.ui.smgr.wagons.WagonsActivity
import com.hfad.smgrapp.update.UpdateManager

class MainActivity : AppCompatActivity() {

    private lateinit var container: ConstraintLayout
    private var isExpanded = false

    private lateinit var updateManager: UpdateManager

    // Ссылка на баннер AdMob — нужна для управления жизненным циклом
    private var adView: AdView? = null

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

        // ── Загружаем баннерную рекламу ─────────────────────────────
        loadBannerAd()
    }

    override fun onResume() {
        super.onResume()
        if (::updateManager.isInitialized) {
            updateManager.checkUpdateInProgress()
        }
        // Возобновляем баннер при возврате в активити
        adView?.resume()
    }

    override fun onPause() {
        // Приостанавливаем баннер, пока активити не видна
        adView?.pause()
        super.onPause()
    }

    override fun onDestroy() {
        // Освобождаем ресурсы баннера при уничтожении активити
        adView?.destroy()
        if (::updateManager.isInitialized) {
            updateManager.unregisterListener()
        }
        super.onDestroy()
    }

    /**
     * Создаёт и загружает адаптивный баннер AdMob.
     * В debug-сборке используется тестовый ID — переключается автоматически.
     */
    private fun loadBannerAd() {
        // Тестовый ID для отладки, реальный — для релиза
        val adUnitId = if (BuildConfig.DEBUG) {
            "ca-app-pub-3940256099942544/6300978111"
        } else {
            "ca-app-pub-9715390414515402/8078293386"
        }

        // Ширина контейнера в dp — нужна для расчёта адаптивного размера
        val adWidth = (resources.displayMetrics.widthPixels / resources.displayMetrics.density).toInt()
        val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)

        adView = AdView(this).apply {
            this.adUnitId = adUnitId
            setAdSize(adSize)
        }

        // Добавляем баннер в контейнер и запрашиваем рекламу
        val adContainer = findViewById<FrameLayout>(R.id.adContainer)
        adContainer.addView(adView)
        adView?.loadAd(AdRequest.Builder().build())
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