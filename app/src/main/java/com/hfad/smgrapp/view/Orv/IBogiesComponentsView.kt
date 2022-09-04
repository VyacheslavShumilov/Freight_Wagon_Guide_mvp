package com.hfad.smgrapp.view.Orv

import com.hfad.smgrapp.model.BogiesComponents

interface IBogiesComponentsView {
    fun onSuccessList(bogiesComponents: ArrayList<BogiesComponents>)
    fun error(errMessage: String)
    fun progress(show: Boolean)
}