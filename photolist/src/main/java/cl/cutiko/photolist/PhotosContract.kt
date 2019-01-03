package cl.cutiko.photolist

import cl.cutiko.data.models.Unsplash

interface PhotosContract {

    interface Callback {
        suspend fun initialRequest()
        fun unsplashesLoaded(unsplashes : List<Unsplash>)
        suspend fun recylerRequest()
    }

    interface Presenter {
        fun startListener()
        suspend fun getRandom()
        suspend fun checkStatus()
    }

}