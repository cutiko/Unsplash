package cl.cutiko.photolist

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
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

    private lateinit var presenter: PhotosPresenter
    private lateinit var adapter: UnsplashesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)
        adapter = UnsplashesAdapter(this)
        val layoutManager = LinearLayoutManager(this)
        unsplashRv.layoutManager = layoutManager
        unsplashRv.setHasFixedSize(true)
        unsplashRv.adapter = adapter
        setScrollListener()
        presenter = PhotosPresenter(application, this, this)
        GlobalScope.launch { presenter.initialLoad() }

    }

    private fun setScrollListener() = unsplashRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            val lastPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
            if (adapter.itemCount - lastPosition <= 2) {
                if (loadingPb.visibility == View.GONE) loadingPb.visibility = View.VISIBLE
                GlobalScope.launch { presenter.getRandom() }
            }
            val colorTo = adapter.getItemColor(lastPosition)
            val bg = photosCl.background as ColorDrawable
            val colorFrom = bg.color
            if (colorFrom == colorTo) return
            val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
            colorAnimation.duration = 400L
            colorAnimation.addUpdateListener { animator -> photosCl.setBackgroundColor(animator.animatedValue as Int) }
            colorAnimation.start()

        }
    })

    override fun unsplashesLoaded(unsplashes: List<Unsplash>?) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                loadingPb.visibility = View.GONE
                adapter.update(unsplashes)
            }
        }
    }

}
