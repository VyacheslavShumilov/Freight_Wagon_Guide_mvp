package com.hfad.smgrapp.view.smgr

import com.hfad.smgrapp.model.Wagons

interface ISmgrView {
    fun onSuccessList(wagons: ArrayList<Wagons>)
    fun error(errMessage: String)
    fun progress(show: Boolean)
}