package cl.cutiko.photolist.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import cl.cutiko.data.models.Unsplash
import cl.cutiko.photolist.R
import cl.cutiko.viewlibrary.LoadingImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class UnsplashesAdapter(context : Context) : RecyclerView.Adapter<UnsplashesAdapter.UnsplashHolder>() {

    private val unsplashes : MutableList<Unsplash> = mutableListOf()
    private val control : MutableSet<String> = mutableSetOf()
    private val black : Int = ContextCompat.getColor(context, android.R.color.black)
    private val white : Int = ContextCompat.getColor(context, android.R.color.white)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_unsplash, parent, false)
        return UnsplashHolder(view)
    }

    override fun onBindViewHolder(holder: UnsplashHolder, position: Int) {
        val unsplash = unsplashes[position]
        val url = unsplash.urls?.small
        holder.loadingIv.setImage(url)

        Picasso.get().load(url).into(object :Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                if (bitmap != null) {
                    val palette = Palette.from(bitmap).generate()
                    val overlayColor = palette.getDarkMutedColor(black)
                    holder.overlay.backgroundTintList = ColorStateList.valueOf(overlayColor)
                    val foregroundColor = palette.getDarkVibrantColor(black)
                    holder.foreground.backgroundTintList = ColorStateList.valueOf(foregroundColor)
                    holder.name.setTextColor(palette.getLightMutedColor(white))
                    holder.likes.setTextColor(palette.getLightVibrantColor(white))
                }
            }
        })
        val user = unsplash.user
        val profile = user?.profile_image?.medium
        Picasso.get().load(profile).centerCrop().fit().into(holder.profile)
        holder.name.text = user?.name
        holder.likes.text = holder.itemView.context.getString(R.string.likes, user?.total_likes)
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
    fun getItemColor(position : Int) : Int {
        return if (position > -1 && position < unsplashes.size) {
            Color.parseColor(unsplashes[position].color)
        } else {
            black
        }
    }

    class UnsplashHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val loadingIv: LoadingImageView = itemView.findViewById(R.id.unsplashIv)
        val overlay : View = itemView.findViewById(R.id.overlayV)
        val foreground : View = itemView.findViewById(R.id.foregroundV)
        val name : TextView = itemView.findViewById(R.id.userNameTv)
        val likes : TextView = itemView.findViewById(R.id.likesTv)
        val profile : ImageView = itemView.findViewById(R.id.profileIv)
    }

}