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
import yuva.assignment.wiproapplication.utils.Repository
import javax.inject.Named

class FactsViewModel  @Inject constructor(private val mUserRepo: Repository): ViewModel() {

    private var factsObj: MutableLiveData<Country>? = null

    val isDataAvailableViewModel: Boolean
        get() = factsObj != null

    val data: LiveData<Country>?
        get() = factsObj

    fun loadFacts() : LiveData<Country> {
        if (factsObj == null) {
//            factsObj = MutableLiveData()
            factsObj=  mUserRepo.getCountyDetails()
        }
        return factsObj as MutableLiveData<Country>
    }

    override fun onCleared() {
        super.onCleared()
        mUserRepo.getCompositeDisposable().clear()
        mUserRepo.getCompositeDisposable().dispose()
    }
}