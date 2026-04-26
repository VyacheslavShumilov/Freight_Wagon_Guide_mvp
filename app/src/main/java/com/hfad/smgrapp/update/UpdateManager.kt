package com.hfad.smgrapp.update

import android.app.Activity
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.hfad.smgrapp.BuildConfig
import com.hfad.smgrapp.R

/**
 * Менеджер обновлений приложения через Google Play In-App Updates API.
 *
 * Защитные механизмы:
 *   • isUpdateSupported — проверяем, есть ли Google Play Services на устройстве.
 *     На AOSP-эмуляторах без Play они отсутствуют, и API In-App Updates
 *     зависает без ответа. Если Play нет — менеджер сразу превращается
 *     в no-op, ничего не блокирует.
 *   • Debug-сборки тоже пропускаем: In-App Updates работают только для
 *     APK, скачанных из Play Store. На debug-сборке проверка обречена.
 *   • Все вызовы Play Core обёрнуты в try-catch — на случай экзотических
 *     состояний устройств (Huawei AppGallery, Russian forks Android и т.п.).
 */
class UpdateManager(
    private val activity: Activity,
    private val updateLauncher: ActivityResultLauncher<IntentSenderRequest>
) {

    /**
     * Условие, при котором имеет смысл вообще обращаться к Play Core.
     * • debug = false (debug-APK не из Play, обновлять нечего)
     * • Google Play Services установлены и работают
     */
    private val isUpdateSupported: Boolean by lazy {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "Debug-сборка — In-App Updates пропущены")
            return@lazy false
        }
        val status = GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(activity)
        if (status != ConnectionResult.SUCCESS) {
            Log.i(TAG, "Google Play Services недоступны (status=$status) — In-App Updates пропущены")
            return@lazy false
        }
        true
    }

    private val appUpdateManager: AppUpdateManager? by lazy {
        if (!isUpdateSupported) null
        else try {
            AppUpdateManagerFactory.create(activity)
        } catch (e: Exception) {
            Log.w(TAG, "Не удалось создать AppUpdateManager: ${e.message}")
            null
        }
    }

    private val installListener = InstallStateUpdatedListener { state ->
        when (state.installStatus()) {
            InstallStatus.DOWNLOADED -> showCompleteUpdateSnackbar()
            InstallStatus.FAILED ->
                Log.e(TAG, "Установка обновления не удалась: ${state.installErrorCode()}")
            else -> { /* DOWNLOADING / INSTALLING — система сама показывает прогресс */ }
        }
    }

    init {
        try {
            appUpdateManager?.registerListener(installListener)
        } catch (e: Exception) {
            Log.w(TAG, "registerListener: ${e.message}")
        }
    }

    /** Главная точка входа. Вызвать в onCreate активити. */
    fun checkForUpdate() {
        val mgr = appUpdateManager ?: return
        try {
            mgr.appUpdateInfo
                .addOnSuccessListener { info -> handleUpdateInfo(info) }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Не удалось проверить обновление: ${e.message}")
                }
        } catch (e: Exception) {
            Log.w(TAG, "checkForUpdate: ${e.message}")
        }
    }

    /**
     * Вызывается из onResume активити.
     * Если пользователь свернул приложение во время скачивания —
     * проверяем, не завершилась ли загрузка в фоне.
     */
    fun checkUpdateInProgress() {
        val mgr = appUpdateManager ?: return
        try {
            mgr.appUpdateInfo.addOnSuccessListener { info ->
                if (info.installStatus() == InstallStatus.DOWNLOADED) {
                    showCompleteUpdateSnackbar()
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "checkUpdateInProgress: ${e.message}")
        }
    }

    fun unregisterListener() {
        try {
            appUpdateManager?.unregisterListener(installListener)
        } catch (e: Exception) {
            Log.w(TAG, "unregisterListener: ${e.message}")
        }
    }

    fun handleUpdateResult(resultCode: Int) {
        if (resultCode != Activity.RESULT_OK) {
            Log.d(TAG, "Обновление отклонено пользователем (resultCode=$resultCode)")
        }
    }

    // ── private ──────────────────────────────────────────────────────────────

    private fun handleUpdateInfo(info: AppUpdateInfo) {
        val isUpdateAvailable =
            info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
        val isFlexibleAllowed =
            info.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)

        if (isUpdateAvailable && isFlexibleAllowed) {
            startFlexibleUpdate(info)
        }
    }

    private fun startFlexibleUpdate(info: AppUpdateInfo) {
        val mgr = appUpdateManager ?: return
        try {
            mgr.startUpdateFlowForResult(
                info,
                updateLauncher,
                AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build()
            )
        } catch (e: Exception) {
            Log.e(TAG, "startFlexibleUpdate: ${e.message}")
        }
    }

    private fun showCompleteUpdateSnackbar() {
        Snackbar.make(
            activity.findViewById(android.R.id.content),
            "Обновление загружено. Перезапустить приложение?",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("Перезапустить") {
                appUpdateManager?.completeUpdate()
            }
            setActionTextColor(activity.getColor(R.color.main_color_dark))
            show()
        }
    }

    companion object {
        private const val TAG = "UpdateManager"
    }
}