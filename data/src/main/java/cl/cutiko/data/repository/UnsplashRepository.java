package cl.cutiko.data.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import cl.cutiko.data.daos.UnsplashDao;
import cl.cutiko.data.database.UnsplashRoomDatabase;
import cl.cutiko.data.models.Unsplash;

import java.util.List;

//TODO move this to Kotlin
public class UnsplashRepository {

    private UnsplashDao unsplashDao;

    public UnsplashRepository(Application application) {
        UnsplashRoomDatabase db = UnsplashRoomDatabase.getDatabase(application);
        unsplashDao = db.getUnsplashDao();
    }

    public void insert(Unsplash unsplash) {
        new UnsplashInsertion(unsplashDao).execute(unsplash);
    }

    public void getAllUnsplash(Callback callback) {
        new UnsplashLoader(unsplashDao, callback).execute();
    }

    public void getLast(LifecycleOwner owner, Observer<Unsplash> observer) {
        unsplashDao.loadLast().observe(owner, observer);
    }

    private static class UnsplashInsertion extends AsyncTask<Unsplash, Void, Boolean> {

        private final UnsplashDao dao;

        UnsplashInsertion(UnsplashDao dao) {
            this.dao = dao;
        }

        @Override
        protected Boolean doInBackground(Unsplash... unsplashes) {
            if (unsplashes.length == 0) return false;
            dao.insertUnsplash(unsplashes[0]);
            return true;
        }
    }

    private static class UnsplashLoader extends AsyncTask<Void, Void, List<Unsplash>> {
        private final UnsplashDao dao;
        private final Callback callback;

        public UnsplashLoader(UnsplashDao dao, Callback callback) {
            this.dao = dao;
            this.callback = callback;
        }

        @Override
        protected List<Unsplash> doInBackground(Void... voids) {
            return dao.loadAllUnsplashes();
        }

        @Override
        protected void onPostExecute(List<Unsplash> unsplashes) {
            callback.all(unsplashes);
        }
    }
    public interface Callback {
        void all(List<Unsplash> unsplashes);
    }

}
