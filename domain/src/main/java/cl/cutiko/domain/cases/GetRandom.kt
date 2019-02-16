package cl.cutiko.domain.cases

import android.app.Application
import cl.cutiko.domain.network.GetUnsplashes

class GetRandom(application: Application)  {

    private val request : GetUnsplashes = GetUnsplashes(application)

    suspend fun start()= request.start()

}