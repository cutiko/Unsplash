package cl.cutiko.domain.network;

import cl.cutiko.data.models.Unsplash;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface Requests {

    @GET("photos/random?orientation=landscape&count=10")
    Call<List<Unsplash>> getRandom();

}
