package arg.movies.mvp

import android.os.Bundle
import android.view.View
import com.bluelinelabs.conductor.Controller

abstract class MvpController<P : Presenter<V>, V : MvpView> : Controller, MvpView {

    protected constructor()
    protected constructor(args: Bundle) : super(args)

    protected var presenter: P

    protected abstract fun providePresenter(): P

    init {
        presenter = providePresenter()
        presenter.onCreate()
    }

    override fun onAttach(view: View) {
        presenter.onAttachView(this as V)
        super.onAttach(view)
    }

    override fun onDetach(view: View) {
        presenter.onDetachView()
        super.onDetach(view)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}