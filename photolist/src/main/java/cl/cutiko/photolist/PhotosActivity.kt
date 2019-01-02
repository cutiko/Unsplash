package cl.cutiko.photolist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import cl.cutiko.data.models.Unsplash
import cl.cutiko.data.repository.UnsplashRepository
import kotlinx.android.synthetic.main.activity_photos.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PhotosActivity : AppCompatActivity(), Observer<Unsplash> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)

        val repository = UnsplashRepository(application)

        GlobalScope.launch { repository.loadAll().map { Log.d("UNSPLASH_DB", it.id) } }

        repository.getLast(this, this)
    }

    override fun onChanged(unsplash: Unsplash?) {
        Log.d("UNSPLASH_DB", "last ${unsplash?.id}")
    }

    fun unsplash() = Unsplash("${System.currentTimeMillis()}", null, null, null, 0, 0, 0)

}
