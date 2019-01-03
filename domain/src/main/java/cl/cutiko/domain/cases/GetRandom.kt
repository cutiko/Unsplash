package cl.cutiko.domain.cases

import android.app.Application
import cl.cutiko.domain.network.GetUnsplashes

class GetRandom(application: Application) : BaseUseCase {

    private val request : GetUnsplashes = GetUnsplashes(application)

    override fun start() {
        //request.start()
    }

}