package cl.cutiko.splash

interface SplashContract {

    interface Callback {
        fun ready()
    }

    interface Presenter {
        suspend fun startRequest()
    }

}