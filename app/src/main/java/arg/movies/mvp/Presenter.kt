package arg.movies.mvp

open class Presenter<View> {

    protected var view: View? = null

    open fun onCreate() {

    }

    open fun onAttachView(view: View) {
        this.view = view
    }

    open fun onDetachView() {
        view = null
    }

    open fun onDestroy() {
    }
}