package cl.cutiko.domain.cases

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import cl.cutiko.data.models.Unsplash
import cl.cutiko.data.repository.UnsplashRepository

class GetLast(
    private val lifecycleOwner: LifecycleOwner,
    private val observer: Observer<Unsplash>,
    application: Application
) : BaseUseCase {

    private val repo: UnsplashRepository = UnsplashRepository(application)

    override fun start() {
        repo.getLast(lifecycleOwner, observer)
    }

}