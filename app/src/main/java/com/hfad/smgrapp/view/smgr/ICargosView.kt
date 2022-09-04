package com.hfad.smgrapp.view.smgr

import com.hfad.smgrapp.model.Cargos
import com.hfad.smgrapp.model.Wagons

interface ICargosView {
    fun onSuccessList(cargos: ArrayList<Cargos>)
    fun error(errMessage: String)
    fun progress(show: Boolean)
}