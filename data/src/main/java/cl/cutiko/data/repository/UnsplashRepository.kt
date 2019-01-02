package cl.cutiko.data.repository

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.LifecycleOwner
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

    suspend fun insert(unsplash : Unsplash ) = coroutineScope{ unsplashDao.insertUnsplash(unsplash) }


    @UiThread
    suspend fun loadAll() : List<Unsplash> = coroutineScope{unsplashDao.loadAllUnsplashes()}

    fun getLast(lifecycleOwner: LifecycleOwner, observer: Observer<Unsplash>) {
        unsplashDao.loadLast().observe(lifecycleOwner, observer)
    }


}