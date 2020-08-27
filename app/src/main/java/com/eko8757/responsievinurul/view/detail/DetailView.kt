package com.eko8757.responsievinurul.view.detail

import android.content.Context
import com.eko8757.responsievinurul.model.data.ResultData

interface DetailView {

    fun getData() {

    }

    fun showData(
        image: String,
        title: String,
        desc: String
    ) {

    }

    interface presenter {
        fun extractData(context: Context, data: ResultData)
    }
}