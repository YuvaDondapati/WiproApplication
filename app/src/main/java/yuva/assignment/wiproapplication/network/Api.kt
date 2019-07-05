package yuva.assignment.wiproapplication.network


import retrofit2.Call
import retrofit2.http.GET
import yuva.assignment.wiproapplication.model.Country

interface Api {

    @get:GET("facts.json")
    val countryFacts: Call<Country>
}