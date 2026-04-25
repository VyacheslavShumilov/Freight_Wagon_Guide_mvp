package com.hfad.smgrapp.update

import android.app.Activity
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.hfad.smgrapp.R

/**
 * Менеджер обновлений приложения через Google Play In-App Updates API.
 *
 * Стратегия — FLEXIBLE update:
 *   • При запуске проверяем наличие новой версии в Google Play.
 *   • Если есть — стартуем фоновую загрузку (пользователь продолжает работать).
 *   • После окончания загрузки показываем Snackbar с кнопкой "Перезапустить".
 *   • Если пользователь сворачивает приложение во время загрузки —
 *     onResume → checkUpdateInProgress() ловит готовность.
 *
 * Использование в MainActivity:
 *
 *     private lateinit var updateManager: UpdateManager
 *
 *     // ВАЖНО: launcher нужно создавать в onCreate ДО updateManager,
 *     // на этапе инициализации — иначе AndroidX упадёт с
 *     // "LifecycleOwners must call register before they are STARTED".
 *     private val updateLauncher =
 *         registerForActivityResult(
 *             ActivityResultContracts.StartIntentSenderForResult()
 *         ) { result -> updateManager.handleUpdateResult(result.resultCode) }
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         setContentView(...)
 *         updateManager = UpdateManager(this, updateLauncher)
 *         updateManager.checkForUpdate()
 *     }
 *
 *     override fun onResume() {
 *         super.onResume()
 *         updateManager.checkUpdateInProgress()
 *     }
 *
 *     override fun onDestroy() {
 *         updateManager.unregisterListener()
 *         super.onDestroy()
 *     }
 */
class UpdateManager(
    private val activity: Activity,
    private val updateLauncher: ActivityResultLauncher<IntentSenderRequest>
) {
    private val appUpdateManager: AppUpdateManager =
        AppUpdateManagerFactory.create(activity)

    private val installListener = InstallStateUpdatedListener { state ->
        when (state.installStatus()) {
            InstallStatus.DOWNLOADED -> showCompleteUpdateSnackbar()
            InstallStatus.FAILED ->
                Log.e(TAG, "Установка обновления не удалась: ${state.installErrorCode()}")
            else -> { /* DOWNLOADING / INSTALLING — система сама показывает прогресс */ }
        }
    }

    init {
        appUpdateManager.registerListener(installListener)
    }

    /** Главная точка входа. Вызвать в onCreate активити. */
    fun checkForUpdate() {
        appUpdateManager.appUpdateInfo
            .addOnSuccessListener { info -> handleUpdateInfo(info) }
            .addOnFailureListener { e ->
                Log.w(TAG, "Не удалось проверить обновление: ${e.message}")
                // Молча игнорируем — обновление это бонус, не критика UX.
            }
    }

    /**
     * Вызывается из onResume активити.
     * Если пользователь свернул приложение во время скачивания —
     * проверяем, не завершилась ли загрузка в фоне, чтобы показать Snackbar.
     */
    fun checkUpdateInProgress() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            if (info.installStatus() == InstallStatus.DOWNLOADED) {
                showCompleteUpdateSnackbar()
            }
        }
    }

    fun unregisterListener() {
        appUpdateManager.unregisterListener(installListener)
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

    /**
     * FIXED — используется новый API Play Core 2.x:
     * appUpdateManager.startUpdateFlow(info, launcher, options).
     *
     * Старый startUpdateFlowForResult(info, type, activity, requestCode)
     * deprecated и предполагал onActivityResult, который тоже deprecated.
     * Новый API принимает launcher напрямую — никаких intentSender.
     */
    private fun startFlexibleUpdate(info: AppUpdateInfo) {
        appUpdateManager.startUpdateFlowForResult(
            info,
            updateLauncher,
            AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build()
        )
    }

    private fun showCompleteUpdateSnackbar() {
        Snackbar.make(
            activity.findViewById(android.R.id.content),
            "Обновление загружено. Перезапустить приложение?",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("Перезапустить") {
                appUpdateManager.completeUpdate()
            }
            setActionTextColor(activity.getColor(R.color.main_color_dark))
            show()
        }
    }

    companion object {
        private const val TAG = "UpdateManager"
    }
}