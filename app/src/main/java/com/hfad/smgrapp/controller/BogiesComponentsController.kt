package com.hfad.smgrapp.controller

import com.hfad.smgrapp.model.BogiesComponents
import com.hfad.smgrapp.service.ApiWagons
import com.hfad.smgrapp.view.orv.components.IBogiesComponentsView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BogiesComponentsController(private val bogiesComponents: IBogiesComponentsView): IBogiesComponentsController {
    private var api = ApiWagons.create()

    override fun onBogiesComponentsList() {
        bogiesComponents.let { view ->
            view.progress(true)
            api.getBogiesComponents().enqueue(object : Callback<ArrayList<BogiesComponents>> {
                override fun onResponse(
                    call: Call<ArrayList<BogiesComponents>>,
                    response: Response<ArrayList<BogiesComponents>>
                ) {
                    if (response.isSuccessful) {
                        view.progress(false)
                        response.body()?.let { data ->
                            view.onSuccessList(data)
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<BogiesComponents>>, t: Throwable) {
                    view.progress(false)
                    view.error("No internet")
                }
            })
        }
    }
}