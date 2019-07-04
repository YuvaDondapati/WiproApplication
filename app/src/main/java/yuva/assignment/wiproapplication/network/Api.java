package yuva.assignment.wiproapplication.network;


import retrofit2.Call;
import retrofit2.http.GET;
import yuva.assignment.wiproapplication.model.Country;

public interface Api {

    @GET("facts.json")
    Call<Country> getCountryFacts();
}