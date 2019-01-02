package cl.cutiko.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.cutiko.data.models.Unsplash

@Dao
interface UnsplashDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUnsplashes(unsplashes : List<Unsplash>?)

    @Query("SELECT * FROM unsplashes")
    fun loadAllUnsplashes() : List<Unsplash>

    @Query("SELECT * FROM unsplashes ORDER BY ID DESC LIMIT 1")
    fun loadLast() : LiveData<Unsplash>
}