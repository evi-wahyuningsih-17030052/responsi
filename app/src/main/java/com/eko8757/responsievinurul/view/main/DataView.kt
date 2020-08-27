package com.eko8757.responsievinurul.view.main

import com.eko8757.responsievinurul.model.data.ResultData

interface DataView {

    fun showDataList(data: ArrayList<ResultData>) {

    }

    interface presenter {

        fun getList() {

        }
    }
}