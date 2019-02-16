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

    @Query("SELECT * FROM unsplashes WHERE bitmaped = 1 ORDER BY ID DESC LIMIT 10")
    fun loadLast() : LiveData<List<Unsplash>>

    @Query("SELECT * FROM unsplashes WHERE bitmaped = 1")
    fun loadAll() : List<Unsplash>
}