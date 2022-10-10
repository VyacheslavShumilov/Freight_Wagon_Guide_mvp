package com.hfad.smgrapp.ui.orv.bogies.impl

import com.hfad.smgrapp.model.Bogies
import com.hfad.smgrapp.service.ApiWagons
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BogiesListPresenterImpl : BogiesListContract.Presenter {

    private var mvpView: BogiesListContract.View? = null
    private var api = ApiWagons.create()

    override fun responseData() {
        mvpView?.let { view ->
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

    override fun attachView(view: BogiesListContract.View) {
        mvpView = view
    }

    override fun detachView() {
        mvpView = null
    }
}