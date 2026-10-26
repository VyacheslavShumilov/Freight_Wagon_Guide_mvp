package com.hfad.smgrapp.web

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import com.google.android.material.color.MaterialColors
import com.hfad.smgrapp.R

/**
 * Открывает интерактивный 3D-справочник вагонов (GitHub Pages).
 *
 * Якоря разделов взяты из исходника сайта (вкладки data-w="…" и
 * обработчик location.hash): gondola, dumpcar, hopper, platform, tank,
 * boxcar, reefer, transporter, bogie.
 */
object Wagons3dLauncher {

    const val BASE_URL = "https://vyacheslavshumilov.github.io/wagons/"

    const val ANCHOR_BOGIE = "bogie"

    /** Открывает сайт (на разделе [anchor], если он задан). */
    fun open(context: Context, anchor: String? = null) {
        val uri = Uri.parse(if (anchor == null) BASE_URL else "$BASE_URL#$anchor")

        // 1. Custom Tabs — если в системе есть браузер с их поддержкой
        if (CustomTabsClient.getPackageName(context, null) != null) {
            try {
                buildCustomTabsIntent(context).launchUrl(context, uri)
                return
            } catch (e: ActivityNotFoundException) {
                // падаем в обычный ACTION_VIEW
            }
        }

        // 2. Любой браузер через ACTION_VIEW, 3. иначе — Toast
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, R.string.wagons3d_no_browser, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Якорь раздела сайта по модели вагона или null, если раздела нет.
     *
     * Род вагона в приложении определяется по префиксу модели — первые
     * 2 символа до "-" (см. WagonsActivity.categories / extractPrefix):
     * "11-066-04" → "11" → крытый.
     * "17" не сопоставлен: в WagonsActivity он одновременно и в
     * «Полувагонах», и в «Бункерных».
     */
    fun anchorFor(model: String): String? {
        val prefix = model.trim().substringBefore("-").take(2)
        return when {
            prefix in setOf("10", "11", "8Т", "Р") -> "boxcar"
            prefix == "12" -> "gondola"
            prefix in setOf("13", "23", "ПМ") -> "platform"
            prefix in setOf("14", "ТМ") -> "transporter"
            prefix in setOf("15", "8Г", "90", "91") || prefix.startsWith("Ж") -> "tank"
            prefix in setOf("16", "МК", "ТН", "ЦБ") -> "reefer"
            prefix in setOf("19", "20", "55", "ВП") -> "hopper"
            prefix in setOf("31", "4В") -> "dumpcar"
            else -> null
        }
    }

    /**
     * Цвет тулбара — colorPrimary текущей темы приложения. Схему Custom Tab
     * выставляем такой же, как у приложения, чтобы цвет и схема совпадали.
     */
    private fun buildCustomTabsIntent(context: Context): CustomTabsIntent {
        val isNight = (context.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        val colorScheme = if (isNight) {
            CustomTabsIntent.COLOR_SCHEME_DARK
        } else {
            CustomTabsIntent.COLOR_SCHEME_LIGHT
        }
        val params = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(
                MaterialColors.getColor(context, androidx.appcompat.R.attr.colorPrimary, 0)
            )
            .build()

        return CustomTabsIntent.Builder()
            .setColorScheme(colorScheme)
            .setColorSchemeParams(colorScheme, params)
            .setDefaultColorSchemeParams(params)
            .setShowTitle(true)
            .setShareState(CustomTabsIntent.SHARE_STATE_ON)
            .build()
    }
}
