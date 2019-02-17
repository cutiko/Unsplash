package cl.cutiko.photolist

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cl.cutiko.photolist.adapters.UnsplashesAdapter

class InfiniteListener(val callback : (colorTo : Int) -> Unit) : RecyclerView.OnScrollListener() {

    companion object {
        private const val THRESHOLD : Int = 7
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val lastPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
        if (recyclerView.adapter == null) return
        val adapter = recyclerView.adapter as UnsplashesAdapter
        if (adapter.itemCount - lastPosition <= THRESHOLD) {
            callback(adapter.getItemColor(lastPosition))
        }
    }
}