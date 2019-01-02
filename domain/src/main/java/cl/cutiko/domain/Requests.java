package cl.cutiko.domain;

import cl.cutiko.data.models.Unsplash;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface Requests {

    @GET("photos/random?orientation=portrait&count=10")
    Call<List<Unsplash>> getRandom();

}
