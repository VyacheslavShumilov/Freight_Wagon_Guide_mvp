package com.hfad.smgrapp.controller

import com.hfad.smgrapp.model.Cargos
import com.hfad.smgrapp.service.ApiWagons
import com.hfad.smgrapp.view.smgr.ICargosView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CargosController(private val cargosView: ICargosView): ICargosController {
    private var api = ApiWagons.create()

    override fun onCargosList() {
        cargosView.let { view ->
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
}