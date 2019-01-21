package cl.cutiko.splash

import android.app.Application
import cl.cutiko.domain.cases.GetRandom
import kotlinx.coroutines.coroutineScope

class SplashPresenter(application: Application, private val callback : SplashContract.Callback) : SplashContract.Presenter {

    private val getRandom : GetRandom = GetRandom(application)

    override suspend fun startRequest() {
        coroutineScope { getRandom.start() }
        callback.ready()
    }


}