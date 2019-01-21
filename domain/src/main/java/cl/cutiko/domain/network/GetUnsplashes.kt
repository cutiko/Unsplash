package cl.cutiko.domain.network

import android.app.Application
import cl.cutiko.data.models.Unsplash
import cl.cutiko.data.repository.UnsplashRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response

class GetUnsplashes(application: Application) {

    private val repo : UnsplashRepository= UnsplashRepository(application)

    suspend fun start() {
        val requests = Interceptor.getInterceptor()
        val call = requests.getRandom()
        coroutineScope{
            val response : Response<List<Unsplash>> = call.execute()
            if (response.isSuccessful && response.code() == 200 && response.body() != null) {
                response.body()?.map {
                }
                GlobalScope.launch { repo.insert(response.body()) }
            }
        }
    }

}