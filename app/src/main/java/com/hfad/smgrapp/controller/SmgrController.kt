package com.hfad.smgrapp.controller

import com.hfad.smgrapp.model.Wagons
import com.hfad.smgrapp.service.Api
import com.hfad.smgrapp.view.ISmgrView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SmgrController(val smgrView: ISmgrView): ISmgrController {
    private var api = Api.create()

    override fun onSmgrList() {
        smgrView.let { view ->
            view.progress(true)
            api.getSmgr().enqueue(object : Callback<ArrayList<Wagons>> {
                override fun onResponse(
                    call: Call<ArrayList<Wagons>>,
                    response: Response<ArrayList<Wagons>>
                ) {
                    if (response.isSuccessful) {
                        view.progress(false)
                        response.body()?.let { data ->
                            view.onSuccessList(data)
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<Wagons>>, t: Throwable) {
                    view.progress(false)
                    view.error("No internet")
                }
            })
        }
    }
}