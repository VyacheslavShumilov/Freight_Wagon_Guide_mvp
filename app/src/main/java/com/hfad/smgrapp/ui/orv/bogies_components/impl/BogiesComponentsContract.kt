package com.hfad.smgrapp.ui.orv.bogies_components.impl


import com.hfad.smgrapp.model.BogiesComponents
import com.hfad.smgrapp.mvp.BaseContract

interface BogiesComponentsContract {
    interface View: BaseContract.View {
        fun onSuccessList(bogiesComponents: ArrayList<BogiesComponents>)
        fun error(errMessage: String)
        fun progress(show: Boolean)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun responseData()
    }

}