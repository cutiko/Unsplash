package cl.cutiko.domain.cases

import android.app.Application
import cl.cutiko.domain.network.GetUnsplashes
import kotlinx.coroutines.coroutineScope

class GetRandom(application: Application)  {

    private val request : GetUnsplashes = GetUnsplashes(application)

    suspend fun start() {
        coroutineScope{request.start()}
    }

}