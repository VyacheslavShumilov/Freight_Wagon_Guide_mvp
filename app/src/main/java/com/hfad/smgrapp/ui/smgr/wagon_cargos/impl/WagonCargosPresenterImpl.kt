package com.hfad.smgrapp.ui.smgr.wagon_cargos.impl

import com.hfad.smgrapp.model.Cargos
import com.hfad.smgrapp.service.ApiWagons
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WagonCargosPresenterImpl : WagonCargosContract.Presenter {

    private var mvpView: WagonCargosContract.View? = null
    private var api = ApiWagons.create()


    override fun responseData() {
        mvpView?.let { view ->
            view.progress(true)
            api.getCargos().enqueue(object : Callback<ArrayList<Cargos>> {
                override fun onResponse(
                    call: Call<ArrayList<Cargos>>,
                    response: Response<ArrayList<Cargos>>
                ) {
                    if (response.isSuccessful) {
                        view.progress(false)
                        response.body()?.let { data ->
                            view.onSuccessList(data)
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<Cargos>>, t: Throwable) {
                    view.progress(false)
                    view.error("No internet")
                }
            })
        }
    }

    override fun attachView(view: WagonCargosContract.View) {
        mvpView = view
    }

    override fun detachView() {
        mvpView = null
    }
}