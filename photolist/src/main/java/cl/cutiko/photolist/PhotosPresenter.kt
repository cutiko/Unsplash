package cl.cutiko.photolist

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import cl.cutiko.data.models.Unsplash
import cl.cutiko.domain.cases.GetLast
import cl.cutiko.domain.cases.GetPrevious
import cl.cutiko.domain.cases.GetRandom
import kotlinx.coroutines.coroutineScope

class PhotosPresenter(
    application: Application,
    lifecycleOwner: LifecycleOwner,
    private val callback : PhotosContract.Callback) : PhotosContract.Presenter, Observer<List<Unsplash>> {

    private val getLast : GetLast
    private val getPrevious : GetPrevious
    private val getRandom : GetRandom

    init {
        getLast = GetLast(lifecycleOwner, this, application)
        getPrevious = GetPrevious(application)
        getRandom = GetRandom(application)
    }

    override suspend fun getRandom() {
        getRandom.start()
    }


    override suspend fun checkStatus() {
        val unsplashes = coroutineScope { getPrevious.getFromDb() }
        if (unsplashes.isNotEmpty()) {
            callback.unsplashesLoaded(unsplashes)
        } else {
            getRandom()
        }
    }

    override fun onChanged(unsplashes: List<Unsplash>) {
        callback.unsplashesLoaded(unsplashes)
    }

    override fun startListener() {
        getLast.start()
    }




}