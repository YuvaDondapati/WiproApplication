package yuva.assignment.wiproapplication.network


import io.reactivex.Observable
import retrofit2.http.GET
import yuva.assignment.wiproapplication.model.Country

interface Api {

    @get:GET("facts.json")
    val countryFacts: Observable<Country>
}