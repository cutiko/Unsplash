package cl.cutiko.splash


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cl.cutiko.viewlibrary.toPhotos


class SplashFragment : Fragment(), SplashContract.Callback, Runnable {

    companion object {
        private const val THREE_SECONDS : Long = 3000
    }

    private val startTime = System.currentTimeMillis()
    private lateinit var presenter: SplashPresenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = SplashPresenter(activity!!.application, this@SplashFragment)
        presenter.startRequest()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun ready() {
        val difference = System.currentTimeMillis() - startTime
        if (difference >= THREE_SECONDS) {
            moveForward()
        } else {
            Handler(Looper.getMainLooper()).postDelayed(this, THREE_SECONDS - difference)
        }
    }

    override fun run() = moveForward()

    private fun moveForward() = findNavController().navigate(toPhotos())

    override fun onStop() {
        presenter.onCancel()
        super.onStop()
    }
}
