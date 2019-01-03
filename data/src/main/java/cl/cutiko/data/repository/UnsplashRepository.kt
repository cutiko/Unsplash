package cl.cutiko.data.repository

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import cl.cutiko.data.daos.UnsplashDao
import cl.cutiko.data.database.UnsplashRoomDatabase
import cl.cutiko.data.models.Unsplash
import kotlinx.coroutines.coroutineScope

class UnsplashRepository(application: Application) {

    private val unsplashDao: UnsplashDao

    init {
        val db = UnsplashRoomDatabase.getDatabase(application)
        unsplashDao = db.unsplashDao
    }

    suspend fun insert(unsplashes : List<Unsplash>? ) = coroutineScope{ unsplashDao.insertUnsplashes(unsplashes) }


    @UiThread
    suspend fun loadAll() : List<Unsplash> = coroutineScope{unsplashDao.loadAllUnsplashes()}

    @UiThread
    fun getLast(lifecycleOwner: LifecycleOwner, observer: Observer<Unsplash>) {
        val liveData : LiveData<Unsplash> = unsplashDao.loadLast()
        if (liveData.hasActiveObservers()) return
        liveData.observe(lifecycleOwner, observer)
    }


}