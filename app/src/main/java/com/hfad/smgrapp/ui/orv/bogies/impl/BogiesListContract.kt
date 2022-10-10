package com.hfad.smgrapp.ui.orv.bogies.impl

import com.hfad.smgrapp.model.Bogies
import com.hfad.smgrapp.mvp.BaseContract

interface BogiesListContract {
    interface View: BaseContract.View {
        fun onSuccessList(bogies: ArrayList<Bogies>)
        fun error(errMessage: String)
        fun progress(show: Boolean)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun responseData()
    }

}