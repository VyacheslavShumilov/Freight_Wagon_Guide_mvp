package com.hfad.smgrapp.ui.smgr.wagon_cargos.impl

import com.hfad.smgrapp.model.Cargos
import com.hfad.smgrapp.mvp.BaseContract

interface WagonCargosContract {
    interface View: BaseContract.View {
        fun onSuccessList(cargos: ArrayList<Cargos>)
        fun error(errMessage: String)
        fun progress(show: Boolean)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun responseData()
    }

}

