package cl.cutiko.domain.cases

import android.app.Application
import cl.cutiko.data.models.Unsplash
import cl.cutiko.data.repository.UnsplashRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class GetPrevious(application: Application) {

    private val repo : UnsplashRepository = UnsplashRepository(application)

    suspend fun getFromDb() : List<Unsplash> = coroutineScope{repo.loadAll() }

}