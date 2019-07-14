package yuva.assignment.wiproapplication.utils

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import yuva.assignment.wiproapplication.model.Country
import yuva.assignment.wiproapplication.network.Api
import javax.inject.Inject

class Repository @Inject constructor(private val api: Api) {

    private var disposable = CompositeDisposable()
    var factsObj = MutableLiveData<Country>()

    fun getCountyDetails() : MutableLiveData<Country>{
        disposable.add(api.countryFacts
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { country -> factsObj.value = country})

        return factsObj
    }

    fun getCompositeDisposable() = disposable
}