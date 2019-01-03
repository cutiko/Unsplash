package cl.cutiko.photolist

import android.os.Bundle
import android.view.View
import android.view.WindowManager
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
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
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
                val lastPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                if (adapter.itemCount - lastPosition <= 2) {
                    if (loadingPb.visibility == View.GONE) loadingPb.visibility = View.VISIBLE
                    GlobalScope.launch { presenter.getRandom() }
                }
                val color = adapter.getItemColor(lastPosition)
                window.statusBarColor = color
                window.navigationBarColor = color
                photosCl.setBackgroundColor(color)
            }
        })
    }

    override fun unsplashesLoaded(unsplashes: List<Unsplash>?) {
        GlobalScope.launch { withContext(Dispatchers.Main) {
            loadingPb.visibility = View.GONE
            adapter.update(unsplashes)
        } }
    }

}
