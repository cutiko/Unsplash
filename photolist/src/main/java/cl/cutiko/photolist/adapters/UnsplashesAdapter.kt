package cl.cutiko.photolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cl.cutiko.data.models.Unsplash
import cl.cutiko.photolist.R
import com.squareup.picasso.Picasso

class UnsplashesAdapter : RecyclerView.Adapter<UnsplashesAdapter.UnsplashHolder>() {

    private val unsplashes : MutableList<Unsplash> = mutableListOf()
    private val control : MutableSet<String> = mutableSetOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_unsplash, parent, false)
        return UnsplashHolder(view)
    }

    override fun onBindViewHolder(holder: UnsplashHolder, position: Int) {
        val unsplash = unsplashes[position]
        val url = unsplash.urls?.small
        Picasso.get().load(url).centerCrop().fit().into(holder.imageView)
        val profile = unsplash.user?.profile_image?.small
        Picasso.get().load(profile).centerCrop().fit().into(holder.profile)
    }

    override fun getItemCount(): Int = unsplashes.size

    fun update(unsplashes : List<Unsplash>?) {
        unsplashes?.map {
            val id = it.id
            if (!control.contains(id)) {
                this.unsplashes.add(it)
                control.add(id)
            }
        }
        notifyDataSetChanged()
    }

    class UnsplashHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.unsplashIv)
        val overlay : View = itemView.findViewById(R.id.overlayV)
        val foreground : View = itemView.findViewById(R.id.foregroundV)
        val name : TextView = itemView.findViewById(R.id.userNameTv)
        val likes : TextView = itemView.findViewById(R.id.likesTv)
        val profile : ImageView = itemView.findViewById(R.id.profileIv)
    }

}