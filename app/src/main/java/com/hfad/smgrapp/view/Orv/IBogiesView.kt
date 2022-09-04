package com.hfad.smgrapp.view.Orv

import com.hfad.smgrapp.model.Bogies

interface IBogiesView {
    fun onSuccessList(bogies: ArrayList<Bogies>)
    fun error(errMessage: String)
    fun progress(show: Boolean)
}