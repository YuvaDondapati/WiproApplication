package yuva.assignment.wiproapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    //this is the data that we will fetch asynchronously
    private var factsObj: MutableLiveData<Country>? = null
    //we will call this method to get the data
    //if the Object is null
    //we will load it asynchronously from server in this method
    //finally we will return the Object
    val facts: LiveData<Country>
        get() {
            if (factsObj == null) {
                factsObj = MutableLiveData()
                loadFacts()
            }
            return factsObj as MutableLiveData<Country>
        }

    val isDataAvailableViewModel: Boolean
        get() = if (factsObj != null) {
            true
        } else {
            false
        }

    val data: LiveData<Country>?
        get() = factsObj

    init {
        MyApplication.netComponent?.inject(this)
//        (application as MyApplication).apiComponent.inject(this)
    }

    //This method is using Retrofit to get the JSON data from URL
    private fun loadFacts() {

        val api = retrofit.create(Api::class.java)
        val call = api.countryFacts


        call.enqueue(object : Callback<Country> {
            override fun onResponse(call: Call<Country>, response: Response<Country>) {
                factsObj!!.value = response.body()
            }

            override fun onFailure(call: Call<Country>, t: Throwable) {

            }
        })
    }

}