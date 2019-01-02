package cl.cutiko.photolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import cl.cutiko.data.models.Unsplash
import cl.cutiko.data.repository.UnsplashRepository

class PhotosActivity : AppCompatActivity(), Runnable {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)

        val repository = UnsplashRepository(application)
        val testing = Unsplash("${System.currentTimeMillis()}", null, null, null, 0, 0, 0)
        repository.insert(testing)

        Handler().postDelayed(this, 5000)
    }

    override fun run() {
        val repository = UnsplashRepository(application)
        repository.getAllUnsplash {
            Toast.makeText(this, "${it.size}", Toast.LENGTH_LONG).show()
        }
    }
}
