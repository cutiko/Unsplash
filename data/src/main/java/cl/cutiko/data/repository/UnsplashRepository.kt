package cl.cutiko.data.repository

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import cl.cutiko.data.daos.UnsplashDao
import cl.cutiko.data.database.UnsplashRoomDatabase
import cl.cutiko.data.models.Unsplash
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class UnsplashRepository(application: Application) {

    private val unsplashDao: UnsplashDao

    init {
        val db = UnsplashRoomDatabase.getDatabase(application)
        unsplashDao = db.unsplashDao
    }

    suspend fun insert(unsplashes : List<Unsplash>? ) = withContext(Dispatchers.IO) { unsplashDao.insertUnsplashes(unsplashes)}


    fun observeLast(lifecycleOwner: LifecycleOwner, observer: Observer<List<Unsplash>>) {
        val liveData : LiveData<List<Unsplash>> = unsplashDao.loadLast()
        if (liveData.hasActiveObservers()) return
        liveData.observe(lifecycleOwner, observer)
    }

    suspend fun loadAll() : List<Unsplash> = coroutineScope{unsplashDao.loadAll()}


}