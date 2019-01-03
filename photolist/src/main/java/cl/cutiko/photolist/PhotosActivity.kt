package cl.cutiko.photolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cl.cutiko.data.models.Unsplash
import cl.cutiko.photolist.adapters.UnsplashesAdapter
import kotlinx.android.synthetic.main.activity_photos.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PhotosActivity : AppCompatActivity(), PhotosContract.Callback {

    private lateinit var photosPresenter : PhotosPresenter
    private val adapter = UnsplashesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)
        unsplashRv.layoutManager = LinearLayoutManager(this)
        unsplashRv.setHasFixedSize(true)
        unsplashRv.adapter = adapter

        photosPresenter = PhotosPresenter(application, this, this)
        GlobalScope.launch { photosPresenter.initialLoad() }

    }


    override fun unsplashesLoaded(unsplashes: List<Unsplash>?) {
        adapter.update(unsplashes)
    }



}
