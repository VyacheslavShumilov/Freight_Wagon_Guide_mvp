package com.hfad.smgrapp.ui.smgr.wagons.impl

import com.hfad.smgrapp.model.Wagons
import com.hfad.smgrapp.service.ApiWagons
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WagonsPresenterImpl: WagonsContract.Presenter {

    private var mvpView: WagonsContract.View? = null
    private var api = ApiWagons.create()

    override fun responseData() {
        mvpView?.let { view ->
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

    override fun attachView(view: WagonsContract.View) {
        mvpView = view
    }

    override fun detachView() {
        mvpView = null
    }
}