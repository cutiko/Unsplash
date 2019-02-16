package cl.cutiko.domain.network

import android.app.Application
import android.util.Log
import cl.cutiko.data.models.Unsplash
import cl.cutiko.data.repository.UnsplashRepository
import com.shopify.promises.Promise
import com.shopify.promises.all
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

class GetUnsplashes(application: Application) {

    private val repo : UnsplashRepository= UnsplashRepository(application)

    suspend fun start() {
        val requests = Interceptor.getInterceptor()
        val call = requests.getRandom()
        withContext(Dispatchers.IO){
            val response : Response<List<Unsplash>> = call.execute()
            if (response.isSuccessful && response.code() == 200 && response.body() != null) {
                val promises = mutableListOf<Promise<Unsplash, Int>>()
                response.body()?.forEachIndexed { index, unsplash ->
                    promises.add(getPromise(unsplash, index))
                }
                Promise.all(promises).whenComplete {result: Promise.Result<Array<Unsplash>, Int> ->
                    when (result) {
                        is Promise.Result.Success<Array<Unsplash>, Int> -> {
                            Log.d("CUTIKO_TAG", "GetUnsplashes.kt it is working")
                            GlobalScope.launch { repo.insert(result.value.asList()) }
                        }
                        is Promise.Result.Error<Array<Unsplash>, Int> -> {
                            Log.d("CUTIKO_TAG", "GetUnsplashes.kt ${result.error} FAILED EVERYTHING")
                        }

                    }
                }
            }
        }
    }

    private fun getPromise(unsplash : Unsplash, index : Int) : Promise<Unsplash, Int> = Promise {
        val requestCreator = Picasso.get().load(unsplash.urls?.small)
        requestCreator.tag(unsplash.id)
        onCancel {
            Picasso.get().cancelTag(unsplash.id)
        }
        try {
            val bitmap = requestCreator.get()
            unsplash.bitmaped = 1
            resolve(unsplash)
            Log.d("CUTIKO_TAG", "GetUnsplashes.kt bitmap obtained for ${unsplash.id}")
            Log.d("CUTIKO_TAG", "${bitmap.byteCount}")
        } catch (e: IOException) {
            Log.d("CUTIKO_TAG", "GetUnsplashes.kt FAIL for ${unsplash.id}")
            reject(index)
            return@Promise
        }
    }

}