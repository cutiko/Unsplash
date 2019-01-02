package cl.cutiko.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.cutiko.data.models.Unsplash

@Dao
interface UnsplashDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUnsplash(vararg unsplash : Unsplash)

    @Query("SELECT * FROM unsplashes")
    fun loadAllUnsplashes() : List<Unsplash>
}