package com.bignerdranch.android.golfapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.bignerdranch.android.golfapp.fragments.initialsetup.FirstSetupWizardFragment

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val PUT_TYPE_CHOICE_TRACKER_KEY = "PUT_TYPE_CHOICE_TRACKER_KEY"
const val PUT_TYPE_CHOICE_VISIBLE_KEY = "PUT_TYPE_CHOICE_VISIBLE_KEY"

class GolfViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle
): AndroidViewModel(application) {

    companion object {
        const val IS_FIRST_LAUNCH_KEY = "IS_FIRST_LAUNCH_KEY"
        const val RANGE_CLASSIFIED_PUTS = "RANGE_CLASSIFIED_PUTS"
        const val CURVE_CLASSIFIED_PUTS = "CURVE_CLASSIFIED PUTS"
        const val PUT_CLASSIFICATION_OFF = "OFF"
    }

    private val sharedPreferences = application.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)

    private val firstSetupQuestionBank: List<String> = listOf(
        application.getString(R.string.setup_question_one),
        application.getString(R.string.setup_question_two),
        application.getString(R.string.setup_question_three),
        application.getString(R.string.setup_question_four),
        application.getString(R.string.setup_question_five),
        application.getString(R.string.setup_question_six)
    )

    var putTypeChoiceTracker
        get() = savedStateHandle[PUT_TYPE_CHOICE_TRACKER_KEY] ?: 0
        set(value) = savedStateHandle.set(PUT_TYPE_CHOICE_TRACKER_KEY, value)

    fun incrementPutChoiceTracker() {
        putTypeChoiceTracker++
    }

    var putTypeChoiceVisible
        get() = savedStateHandle[PUT_TYPE_CHOICE_VISIBLE_KEY] ?: false
        set(value) = savedStateHandle.set(PUT_TYPE_CHOICE_VISIBLE_KEY, value)

    val currentQuestionText: String
        get() = firstSetupQuestionBank[currentIndex]

    var currentIndex
        get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    fun getQuestionBankSize(): Int {
        return firstSetupQuestionBank.size
    }

    fun moveToNext() {
        if (currentIndex == 3 && !getPutTracking()) {
            currentIndex = (currentIndex + 1)
            currentIndex = (currentIndex + 1)
            setPutTypeTracking(false)
            setPutTypeClassification(PUT_CLASSIFICATION_OFF)
        }
        if (currentIndex == 4 && !getPutTypeTracking()) {
            currentIndex = (currentIndex + 1)
            setPutTypeClassification(PUT_CLASSIFICATION_OFF)
        }
        currentIndex = (currentIndex + 1)
    }

    fun registerBooleanAnswer(answer: Boolean) {
        when (currentIndex) {
            0 -> setFairwayTracking(answer)
            1 -> setGirTracking(answer)
            2 -> setPenaltyTracking(answer)
            3 -> setPutTracking(answer)
            4 -> setPutTypeTracking(answer)
        }
    }

    fun registerStringAnswer(answer: String) {
        when (currentIndex) {
            5 -> setPutTypeClassification(answer)
        }
    }

    fun getIsFirstLaunch(): Boolean {
        return sharedPreferences.getBoolean(IS_FIRST_LAUNCH_KEY, true)
    }

    fun setIsFirstLaunch(isFirstLaunchValue: Boolean) {
        sharedPreferences.edit().putBoolean(IS_FIRST_LAUNCH_KEY, isFirstLaunchValue).apply()
    }

//    TODO: make constants out of these keys
    fun getFairwayTracking(): Boolean {
        return sharedPreferences.getBoolean("trackFairwayHits", true)
    }

    fun setFairwayTracking(fairwayTrackingEnabled: Boolean) {
        sharedPreferences.edit().putBoolean("trackFairwayHits", fairwayTrackingEnabled).apply()
    }

    fun getGirTracking(): Boolean {
        return sharedPreferences.getBoolean("trackGir", true)
    }

    fun setGirTracking(girTrackingEnabled: Boolean) {
        sharedPreferences.edit().putBoolean("trackGir", girTrackingEnabled).apply()
    }

    fun getPenaltyTracking(): Boolean {
        return sharedPreferences.getBoolean("trackPenaltyHits", true)
    }

    fun setPenaltyTracking(penaltyTrackingEnabled: Boolean) {
        sharedPreferences.edit().putBoolean("trackPenaltyHits", penaltyTrackingEnabled).apply()
    }

    fun getPutTracking(): Boolean {
        return sharedPreferences.getBoolean("trackPut", true)
    }

    fun setPutTracking(putTrackingEnabled: Boolean) {
        sharedPreferences.edit().putBoolean("trackPut", putTrackingEnabled).apply()
    }

    fun getPutTypeTracking(): Boolean {
        return sharedPreferences.getBoolean("trackPutTypes", true)
    }

    fun setPutTypeTracking(putTypeTrackingEnabled: Boolean) {
        sharedPreferences.edit().putBoolean("trackPutTypes", putTypeTrackingEnabled).apply()
    }

    fun getPutTypeClassification(): String? {
        return sharedPreferences.getString("putTypeClassification", "off")
    }

    fun setPutTypeClassification(putTypeClassificationName: String) {
        sharedPreferences.edit().putString("putTypeClassification", putTypeClassificationName).apply()
    }

}