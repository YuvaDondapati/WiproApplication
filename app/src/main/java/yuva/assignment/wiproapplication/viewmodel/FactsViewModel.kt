package yuva.assignment.wiproapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import yuva.assignment.wiproapplication.model.Country
import yuva.assignment.wiproapplication.utils.Repository

class FactsViewModel  @Inject constructor(private val mUserRepo: Repository): ViewModel() {

    private var factsObj: MutableLiveData<Country>? = null

    val isDataAvailableViewModel: Boolean
        get() = factsObj != null

    val data: LiveData<Country>?
        get() = factsObj

    fun loadFacts() : LiveData<Country> {
        if (factsObj == null) {
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