package cl.cutiko.photolist

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import cl.cutiko.data.models.Unsplash
import cl.cutiko.domain.cases.GetLast
import cl.cutiko.domain.cases.GetPrevious
import cl.cutiko.domain.cases.GetRandom
import kotlinx.coroutines.*

class PhotosPresenter(
    application: Application,
    lifecycleOwner: LifecycleOwner,
    private val callback : PhotosContract.Callback) : PhotosContract.Presenter, Observer<List<Unsplash>> {

    private val getLast : GetLast
    private val getRandom : GetRandom
    private val getPrevious : GetPrevious
    private var randomCalled = false
    private val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    init {
        getLast = GetLast(lifecycleOwner, this, application)
        getRandom = GetRandom(application)
        getPrevious = GetPrevious(application)
    }

    override fun initialLoad() {
        uiScope.launch {
            val unsplashes = getPrevious.getFromDb()
            onChanged(unsplashes)
        }
        getLast.start()
    }

    override fun getRandom() {
        if (!randomCalled) {
            randomCalled = true
            uiScope.launch { getRandom.start() }
        }
    }

    override fun onChanged(unsplashes: List<Unsplash>?) {
        randomCalled = false
        callback.unsplashesLoaded(unsplashes)
    }

    override fun onCancel() {
        uiScope.coroutineContext.cancelChildren()
    }

}