package ru.samitin.mytranslater.view.main



import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import ru.samitin.mytranslater.model.data.AppState
import ru.samitin.mytranslater.model.datasource.DataSourceRemote
import ru.samitin.mytranslater.model.repository.RepositoryImplementation
import ru.samitin.mytranslater.presenter.Presenter
import ru.samitin.mytranslater.rx.SchedulerProvider
import ru.samitin.mytranslater.view.base.View
import ru.samitin.mytranslaterr.model.datasource.DataSourceLocal

class MainPresenterImpl<T : AppState, V : View>(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : Presenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { currentView?.renderData(AppState.Loading(null)) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(appState: AppState) {
                currentView?.renderData(appState)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(AppState.Error(e))
            }

            override fun onComplete() {
            }
        }
    }
}
