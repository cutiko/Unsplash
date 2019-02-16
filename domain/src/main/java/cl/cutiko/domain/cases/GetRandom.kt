package cl.cutiko.domain.cases

import android.app.Application
import cl.cutiko.domain.network.GetUnsplashes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetRandom(application: Application)  {

    private val request : GetUnsplashes = GetUnsplashes(application)

    suspend fun start()= withContext(Dispatchers.IO){request.start()}

}