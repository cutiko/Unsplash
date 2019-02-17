package cl.cutiko.domain.network

import android.app.Application
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import cl.cutiko.data.models.Unsplash
import cl.cutiko.data.models.UnsplashSwatch
import cl.cutiko.data.repository.UnsplashRepository
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.IOException

class GetUnsplashes(application: Application) {

    private val repo: UnsplashRepository = UnsplashRepository(application)
    private val black : Int
    private val white : Int

    init {
        val context = application.applicationContext
        black = ContextCompat.getColor(context, android.R.color.black)
        white = ContextCompat.getColor(context, android.R.color.white)
    }

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
            val photo = Picasso.get().load(unsplash.urls?.small).get()
            unsplash.bitmaped = true
            val palette = Palette.from(photo).generate()
            val muted = palette.getDarkMutedColor(black)
            val vibrant = palette.getDarkVibrantColor(black)
            val darkSwatch = palette.darkMutedSwatch

            var body = black
            var title = white
            if (darkSwatch != null) {
                body = darkSwatch.bodyTextColor
                title = darkSwatch.titleTextColor
            }
            val photoSwatch = UnsplashSwatch(muted, vibrant, body , title)
            unsplash.swatch = photoSwatch
            unsplash.downloaded_at = System.currentTimeMillis()
        } catch (exception: IOException) {
            Log.d("CUTIKO_TAG", "GetUnsplashes.kt", exception)
        }
    }
}