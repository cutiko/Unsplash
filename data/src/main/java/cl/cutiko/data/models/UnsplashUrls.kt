package cl.cutiko.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UnsplashUrls(
    @PrimaryKey var small: String,
    private var thumb: String?,
    private var raw: String?,
    private var regular: String?,
    private var full: String?
)