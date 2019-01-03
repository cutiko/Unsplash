package cl.cutiko.photolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cl.cutiko.data.models.Unsplash
import cl.cutiko.photolist.adapters.UnsplashesAdapter
import kotlinx.android.synthetic.main.activity_photos.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotosActivity : AppCompatActivity(), PhotosContract.Callback {

    private lateinit var presenter : PhotosPresenter
    private val adapter = UnsplashesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)
        val layoutManager = LinearLayoutManager(this)
        unsplashRv.layoutManager = layoutManager
        unsplashRv.setHasFixedSize(true)
        unsplashRv.adapter = adapter
        setScrollListener(unsplashRv, layoutManager)
        presenter = PhotosPresenter(application, this, this)
        GlobalScope.launch { presenter.initialLoad() }
    }

    private fun setScrollListener(recycler : RecyclerView, layoutManager: LinearLayoutManager) {
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (adapter.itemCount - layoutManager.findLastCompletelyVisibleItemPosition() <= 10) {
                    GlobalScope.launch { presenter.getRandom() }
                }
            }
        })
    }

    override fun unsplashesLoaded(unsplashes: List<Unsplash>?) {
        GlobalScope.launch { withContext(Dispatchers.Main) {
            adapter.update(unsplashes)
        } }
    }



}
