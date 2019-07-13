package yuva.assignment.wiproapplication.viewmodel

import android.annotation.SuppressLint
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import yuva.assignment.wiproapplication.app.MyApplication
import yuva.assignment.wiproapplication.model.Country
import yuva.assignment.wiproapplication.network.Api
import javax.inject.Named

class FactsViewModel internal constructor() : ViewModel() {

    @Inject
    lateinit var retrofit: Retrofit
    private var factsObj: MutableLiveData<Country>? = null

    private var disposable = CompositeDisposable()

    /*
    To retrive data from server
     */
    val facts: LiveData<Country>
        get() {
            if (factsObj == null) {
                factsObj = MutableLiveData()
                loadFacts()
            }
            return factsObj as MutableLiveData<Country>
        }

    val isDataAvailableViewModel: Boolean
        get() = factsObj != null

    val data: LiveData<Country>?
        get() = factsObj

    init {
        MyApplication.netComponent?.inject(this)
    }

    fun loadFacts() {
        val api = retrofit.create(Api::class.java)
        disposable.add(api.countryFacts
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { country -> factsObj!!.value = country})
    }

}