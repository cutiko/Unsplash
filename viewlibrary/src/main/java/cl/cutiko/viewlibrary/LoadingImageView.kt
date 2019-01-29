package cl.cutiko.viewlibrary

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class LoadingImageView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs), Callback {

    companion object {
        private const val DURATION : Long = 400L
        private const val SOLID : Float = 1F
        private const val TRANSPARENT : Float = 0F
    }

    private val imageView = ImageView(context)
    private val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyle)

    init {
        imageView.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        (imageView as View).alpha = TRANSPARENT
        val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        progressBar.layoutParams = layoutParams
        addView(imageView)
        addView(progressBar)
    }

    fun setImage(url: String?) = Picasso.get().load(url).centerCrop().fit().into(imageView, this)

    override fun onSuccess() {
        imageView.animate().alpha(SOLID).setDuration(DURATION).start()
        progressBar.animate().alpha(TRANSPARENT).setDuration(DURATION).setListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {progressBar.visibility = View.GONE}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}

        }).start()
    }

    override fun onError(e: Exception?) {
    }

}