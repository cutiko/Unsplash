package cl.cutiko.photolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import cl.cutiko.data.models.Unsplash
import cl.cutiko.photolist.R
import com.squareup.picasso.Picasso

class UnsplashesAdapter : RecyclerView.Adapter<UnsplashesAdapter.UnsplashHolder>() {

    private val unsplashes : MutableList<Unsplash> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_unsplash, parent, false)
        return UnsplashHolder(view)
    }

    override fun onBindViewHolder(holder: UnsplashHolder, position: Int) {
        val unsplash = unsplashes[position]
        val url = unsplash.urls?.small
        Picasso.get().load(url).centerCrop().fit().into(holder.imageView)
    }

    override fun getItemCount(): Int = unsplashes.size

    fun update(unsplashes : List<Unsplash>?) {
        if (unsplashes == null) return
        this.unsplashes.addAll(unsplashes.asIterable())
    }

    class UnsplashHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView : ImageView = itemView as ImageView
    }

}