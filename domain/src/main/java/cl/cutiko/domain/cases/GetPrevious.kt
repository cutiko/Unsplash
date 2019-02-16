package cl.cutiko.domain.cases

import android.app.Application
import cl.cutiko.data.models.Unsplash
import cl.cutiko.data.repository.UnsplashRepository

class GetPrevious(application: Application) {

    private val repo : UnsplashRepository = UnsplashRepository(application)

    suspend fun getFromDb() : List<Unsplash> = repo.loadAll()

}