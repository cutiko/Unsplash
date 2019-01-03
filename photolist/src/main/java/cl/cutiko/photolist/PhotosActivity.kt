package cl.cutiko.photolist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cl.cutiko.data.models.Unsplash
import kotlinx.android.synthetic.main.activity_photos.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PhotosActivity : AppCompatActivity(), PhotosContract.Callback {

    lateinit var photosPresenter : PhotosPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)
        photosPresenter = PhotosPresenter(application, this, this)
        photosPresenter.startListener()
        GlobalScope.launch { initialRequest() }
        testBtn.setOnClickListener { GlobalScope.launch { photosPresenter.getRandom() } }
    }

    override fun unsplashesLoaded(unsplashes: List<Unsplash>) {
        unsplashes.map {
            Log.d("UNSPLASH_DB", it.id)
        }
    }


    override suspend fun recyclerRequest() {
        photosPresenter.getRandom()
    }


    override suspend fun initialRequest() {
        photosPresenter.loadPrevious()
    }

    override fun unsplashLoaded(unsplash: Unsplash) {
        Log.d("UNSPLASH_DB", "single: ${unsplash.id}")
    }


}
