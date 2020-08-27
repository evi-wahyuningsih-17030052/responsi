package com.eko8757.responsievinurul.presenter.data

import com.eko8757.responsievinurul.model.data.ResponseData
import com.eko8757.responsievinurul.model.data.ResultData
import com.eko8757.responsievinurul.service.BaseApi
import com.eko8757.responsievinurul.utils.Constants
import com.eko8757.responsievinurul.view.main.DataView
import com.eko8757.responsievinurul.view.main.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class PresenterData(val view: DataView, val mainView: MainView, private val factory: BaseApi) : DataView.presenter {

    private var mCompositeDisposable: CompositeDisposable? = null

    override fun getList() {
        mainView.showLoading()
        mCompositeDisposable = CompositeDisposable()
        mCompositeDisposable?.add(factory.getDataList(Constants.API_KEY, "en")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : DisposableObserver<Response<ResponseData>>() {
                override fun onComplete() {
                    mainView.hideLoading()
                }

                override fun onNext(t: Response<ResponseData>) {
                    if (t.body()?.results != null) {
                        mainView.hideLoading()
                        view.showDataList(t.body()?.results as ArrayList<ResultData>)
                    } else {
                        mainView.hideLoading()
                    }
                }

                override fun onError(e: Throwable) {
                    mainView.hideLoading()
                    mainView.showMessage(e.message.toString())
                }
            }))
    }
}