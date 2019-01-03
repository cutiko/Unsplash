package cl.cutiko.domain.network

import android.app.Application
import cl.cutiko.data.models.Unsplash
import cl.cutiko.data.repository.UnsplashRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetUnsplashes(application: Application) : Callback<List<Unsplash>> {

    private val repo : UnsplashRepository= UnsplashRepository(application)

    fun start() {
        val requests = Interceptor.getInterceptor()
        val call = requests.getRandom()
        call.enqueue(this)
    }

    override fun onResponse(call: Call<List<Unsplash>>, response: Response<List<Unsplash>>) {
        if (response.isSuccessful && response.code() == 200 && response.body() != null) {
            GlobalScope.launch { repo.insert(response.body()) }
        }
    }

    override fun onFailure(call: Call<List<Unsplash>>, t: Throwable) {}

}