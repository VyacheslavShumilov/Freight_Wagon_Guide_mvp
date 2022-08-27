package com.hfad.smgrapp

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.hfad.smgrapp.navigator.AppNavigator
import com.hfad.smgrapp.navigator.AppNavigatorImpl

class ServicesLocator() {
    fun providerNavigator(fragmentActivity: FragmentActivity):AppNavigator{
        return AppNavigatorImpl(fragmentActivity)
    }
}