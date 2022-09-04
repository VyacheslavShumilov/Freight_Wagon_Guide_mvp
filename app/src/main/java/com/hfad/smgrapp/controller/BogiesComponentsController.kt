package com.hfad.smgrapp.controller

import com.hfad.smgrapp.model.Bogies
import com.hfad.smgrapp.service.ApiWagons
import com.hfad.smgrapp.view.Orv.IBogiesComponentsView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BogiesComponentsController(private val bogiesComponents: IBogiesComponentsView): IBogiesComponentsController {
    private var api = ApiWagons.create()

    override fun onBogiesList() {
        bogiesComponents.let { view ->
            view.progress(true)
            api.getBogies().enqueue(object : Callback<ArrayList<Bogies>> {
                override fun onResponse(
                    call: Call<ArrayList<Bogies>>,
                    response: Response<ArrayList<Bogies>>
                ) {
                    if (response.isSuccessful) {
                        view.progress(false)
                        response.body()?.let { data ->
                            view.onSuccessList(data)
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<Bogies>>, t: Throwable) {
                    view.progress(false)
                    view.error("No internet")
                }
            })
        }
    }
}