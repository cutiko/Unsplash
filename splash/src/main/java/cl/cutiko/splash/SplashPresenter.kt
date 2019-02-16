package cl.cutiko.splash

import android.app.Application
import cl.cutiko.domain.cases.GetRandom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class SplashPresenter(application: Application, private val callback : SplashContract.Callback) : SplashContract.Presenter {

    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val getRandom : GetRandom = GetRandom(application)

    override fun startRequest() {
        uiScope.launch { getRandom.start() }
        callback.ready()
    }

    override fun onCancel() {
        uiScope.coroutineContext.cancelChildren()
    }



}