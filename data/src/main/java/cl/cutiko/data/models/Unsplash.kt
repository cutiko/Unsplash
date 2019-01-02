package cl.cutiko.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Unsplash(
    @PrimaryKey var id: String,
    var urls: UnsplashUrls?,
    var color: String?,
    var description: String?,
    var downloads: Int = 0,
    var likes: Int = 0,
    var views: Int = 0,
    @Embedded var links: UnsplashLinks?
)