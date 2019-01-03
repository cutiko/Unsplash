package cl.cutiko.photolist

import android.os.Bundle
import android.util.Log
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
    private lateinit var adapter : UnsplashesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)
        adapter = UnsplashesAdapter(this)
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
                Log.d("SCROLLING_LISTENER", "")
                if (adapter.itemCount - layoutManager.findLastCompletelyVisibleItemPosition() <= 2) {
                    GlobalScope.launch { presenter.getRandom() }
                    Log.d("SCROLLING_LISTENER", "CONDITION")
                }
            }
        })
    }

    override fun unsplashesLoaded(unsplashes: List<Unsplash>?) {
        Log.d("SCROLLING_LISTENER", "NEW DATA")
        GlobalScope.launch { withContext(Dispatchers.Main) {
            Log.d("SCROLLING_LISTENER", "INSIDE COROUTINE")
            adapter.update(unsplashes)
        } }
    }



}
