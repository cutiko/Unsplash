package cl.cutiko.photolist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import cl.cutiko.data.models.Unsplash
import cl.cutiko.data.repository.UnsplashRepository
import kotlinx.android.synthetic.main.activity_photos.*

class PhotosActivity : AppCompatActivity(), Observer<Unsplash>, UnsplashRepository.Callback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)

        val repository = UnsplashRepository(application)
        repository.getAllUnsplash(this)

        repository.getLast(this, this)

        testBtn.setOnClickListener {
            repository.insert(unsplash())
        }
    }

    override fun onChanged(unsplash: Unsplash?) {
        Log.d("UNSPLASH_DB", "last ${unsplash?.id}")
    }

    fun unsplash() = Unsplash("${System.currentTimeMillis()}", null, null, null, 0, 0, 0)

    override fun all(list: MutableList<Unsplash>?) {
        Log.d("UNSPLASH_DB", "${list?.size}")
        list?.map {
            Log.d("UNSPLASH_DB", it.id)
        }
    }
}
