package com.hfad.smgrapp.review

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory

class AppReviewManager(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val reviewManager: ReviewManager = ReviewManagerFactory.create(context)

    fun maybeRequestReview(activity: Activity) {
        val launchCount = prefs.getInt(KEY_LAUNCH_COUNT, 0) + 1
        prefs.edit().putInt(KEY_LAUNCH_COUNT, launchCount).apply()

        // Показываем диалог только на определённых запусках, чтобы не раздражать
        if (launchCount in TRIGGER_LAUNCHES) {
            Handler(Looper.getMainLooper()).postDelayed({
                requestReview(activity)
            }, REQUEST_DELAY_MS)
        }
    }

    private fun requestReview(activity: Activity) {
        reviewManager.requestReviewFlow()
            .addOnCompleteListener { request ->
                if (request.isSuccessful) {
                    val reviewInfo: ReviewInfo = request.result
                    reviewManager.launchReviewFlow(activity, reviewInfo)
                        .addOnCompleteListener {
                            Log.d(TAG, "Review flow завершён")
                        }
                } else {
                    Log.w(TAG, "Не удалось запросить review flow: ${request.exception?.message}")
                }
            }
    }

    companion object {
        private const val TAG = "AppReviewManager"
        private const val PREFS_NAME = "app_review_prefs"
        private const val KEY_LAUNCH_COUNT = "launch_count"
        private const val REQUEST_DELAY_MS = 1500L
        private val TRIGGER_LAUNCHES = setOf(5, 15, 30)
    }
}
