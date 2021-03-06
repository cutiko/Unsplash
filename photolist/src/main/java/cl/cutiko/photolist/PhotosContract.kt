package cl.cutiko.photolist

import cl.cutiko.data.models.Unsplash

interface PhotosContract {

    interface Callback {
        fun unsplashesLoaded(unsplashes : List<Unsplash>?)
    }

    interface Presenter {
        fun getRandom()
        fun initialLoad()
        fun onCancel()
    }

}