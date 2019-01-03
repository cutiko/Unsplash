package cl.cutiko.photolist

import cl.cutiko.data.models.Unsplash

interface PhotosContract {

    interface Callback {
        suspend fun initialRequest()
        fun unsplashesLoaded(unsplashes : List<Unsplash>)
        fun unsplashLoaded(unsplash: Unsplash)
        suspend fun recyclerRequest()
    }

    interface Presenter {
        fun startListener()
        suspend fun getRandom()
        suspend fun loadPrevious()
    }

}