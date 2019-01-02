package cl.cutiko.data.repository;

import android.app.Application;
import android.os.AsyncTask;
import cl.cutiko.data.daos.UnsplashDao;
import cl.cutiko.data.database.UnsplashRoomDatabase;
import cl.cutiko.data.models.Unsplash;

import java.util.List;
import java.util.concurrent.Executor;

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
        new UnsplasLoader(unsplashDao, callback).execute();
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

    private static class UnsplasLoader extends AsyncTask<Void, Void, List<Unsplash>> {
        private final UnsplashDao dao;
        private final Callback callback;

        public UnsplasLoader(UnsplashDao dao, Callback callback) {
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
