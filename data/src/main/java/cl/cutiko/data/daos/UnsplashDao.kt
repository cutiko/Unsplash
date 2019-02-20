package cl.cutiko.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.cutiko.data.models.Unsplash

@Dao
interface UnsplashDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUnsplashes(unsplashes : List<Unsplash>?)

    @Query("SELECT * FROM unsplashes WHERE downloaded_at > :downloadedAt ORDER BY downloaded_at LIMIT 10")
    fun loadLast(downloadedAt : Long) : LiveData<List<Unsplash>>

    @Query("SELECT * FROM unsplashes ORDER BY downloaded_at")
    fun loadAll() : List<Unsplash>
}