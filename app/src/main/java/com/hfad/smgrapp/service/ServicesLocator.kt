package com.hfad.smgrapp.service


import androidx.fragment.app.FragmentActivity
import com.hfad.smgrapp.navigator.AppNavigator
import com.hfad.smgrapp.navigator.AppNavigatorImpl
import com.hfad.smgrapp.navigator.AppNavigatorParam

class ServicesLocator {
    fun providerNavigator(fragmentActivity: FragmentActivity):AppNavigator{
        return AppNavigatorImpl(fragmentActivity)
    }

    fun providerNavigatorParam(fragmentActivity: FragmentActivity): AppNavigatorParam {
        return AppNavigatorImpl(fragmentActivity)
    }
}