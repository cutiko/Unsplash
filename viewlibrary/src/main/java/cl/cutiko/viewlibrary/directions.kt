package cl.cutiko.viewlibrary

import android.os.Bundle
import androidx.navigation.NavDirections

fun toPhotos() = object : NavDirections {
    override fun getActionId(): Int  = R.id.to_photos
    override fun getArguments(): Bundle? = null
}