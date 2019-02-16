package cl.cutiko.domain.network

import android.app.Application
import android.util.Log
import cl.cutiko.data.models.Unsplash
import cl.cutiko.data.repository.UnsplashRepository
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.IOException

class GetUnsplashes(application: Application) {

    private val repo: UnsplashRepository = UnsplashRepository(application)

    suspend fun start() {
        val requests = Interceptor.getInterceptor()
        val call = requests.getRandom()
        withContext(Dispatchers.IO) {
            try {
                val response: Response<List<Unsplash>> = call.execute()
                val unsplashes = response.body()
                if (!response.isSuccessful || response.code() != 200 || unsplashes == null) return@withContext
                val deferred = unsplashes.map {getAsync(it)}
                deferred.awaitAll()
                repo.insert(unsplashes)
            } catch (exception: IOException) {
                Log.d("CUTIKO_TAG", "GetUnsplashes.kt", exception)
            }
        }

    }

    private fun getAsync(unsplash: Unsplash) = CoroutineScope(Dispatchers.IO).async {
        try {
            Picasso.get().load(unsplash.urls?.small).get()
            unsplash.bitmaped = true
        } catch (exception: IOException) {
            Log.d("CUTIKO_TAG", "GetUnsplashes.kt", exception)
        }
    }
}