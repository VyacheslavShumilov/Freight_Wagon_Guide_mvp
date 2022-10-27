package com.hfad.smgrapp.mvp

class BaseContract {
    interface Presenter<in T>{
        fun attachView(view:T)
        fun detachView()
    }

    interface View{
    }
}