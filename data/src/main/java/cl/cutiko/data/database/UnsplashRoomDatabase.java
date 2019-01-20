package cl.cutiko.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import cl.cutiko.data.daos.UnsplashDao;
import cl.cutiko.data.models.Unsplash;


@Database(entities = {Unsplash.class}, version = 1, exportSchema = false)
public abstract class UnsplashRoomDatabase extends RoomDatabase {

    private static volatile UnsplashRoomDatabase INSTANCE;

    public abstract UnsplashDao getUnsplashDao();

    public static UnsplashRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UnsplashRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UnsplashRoomDatabase.class, "unsplash_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
