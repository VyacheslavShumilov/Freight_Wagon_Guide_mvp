package com.hfad.smgrapp.ui.orv.bogies_components.impl

import com.hfad.smgrapp.model.BogiesComponents
import com.hfad.smgrapp.service.ApiWagons
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BogiesComponentsPresenterImpl : BogiesComponentsContract.Presenter {

    private var mvpView: BogiesComponentsContract.View? = null
    private var api = ApiWagons.create()

    override fun responseData() {
        mvpView?.let { view ->
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

    override fun attachView(view: BogiesComponentsContract.View) {
        mvpView = view
    }

    override fun detachView() {
        mvpView = null
    }
}