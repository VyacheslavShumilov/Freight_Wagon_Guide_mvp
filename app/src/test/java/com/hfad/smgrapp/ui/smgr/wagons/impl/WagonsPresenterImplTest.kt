package com.hfad.smgrapp.ui.smgr.wagons.impl

import com.hfad.smgrapp.mvp.BaseContract
import com.hfad.smgrapp.service.ApiWagons
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class WagonsPresenterImplTest {

    private lateinit var presenter: WagonsPresenterImpl

    @Mock
    private lateinit var wagonsContract: WagonsContract

    @Mock
    private lateinit var apiWagons: ApiWagons



    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = WagonsPresenterImpl()
    }

    @Test
    fun responseData_Test() {

    }

    @Test
    fun attachView_Test() {
    }

    @Test
    fun detachView_Test() {

    }
}