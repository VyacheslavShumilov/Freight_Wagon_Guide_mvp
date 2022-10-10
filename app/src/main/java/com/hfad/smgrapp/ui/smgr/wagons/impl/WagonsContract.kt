package com.hfad.smgrapp.ui.smgr.wagons.impl

import com.hfad.smgrapp.model.Wagons
import com.hfad.smgrapp.mvp.BaseContract

interface WagonsContract {
    interface View: BaseContract.View {
        fun onSuccessList(wagons: ArrayList<Wagons>)
        fun error(errMessage: String)
        fun progress(show: Boolean)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun responseData()
    }
}