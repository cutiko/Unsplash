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

    init {
        getLast = GetLast(lifecycleOwner, this, application)
        getRandom = GetRandom(application)
        getPrevious = GetPrevious(application)
    }

    override suspend fun initialLoad() {
        val unsplashes = coroutineScope { getPrevious.getFromDb() }
        onChanged(unsplashes)
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                getLast.start()
            }
        }
    }

    override suspend fun getRandom() {
        if (!randomCalled) {
            randomCalled = true
            getRandom.start()
        }
    }

    override fun onChanged(unsplashes: List<Unsplash>?) {
        randomCalled = false
        callback.unsplashesLoaded(unsplashes)
    }

}