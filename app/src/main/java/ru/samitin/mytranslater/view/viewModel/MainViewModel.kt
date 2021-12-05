package ru.samitin.mytranslater.view.viewModel

import androidx.lifecycle.LiveData
import io.reactivex.observers.DisposableObserver
import ru.samitin.mytranslater.model.data.AppState
import ru.samitin.mytranslater.model.datasource.DataSourceRemote
import ru.samitin.mytranslater.model.repository.RepositoryImplementation
import ru.samitin.mytranslater.interactor.MainInteractor
import ru.samitin.mytranslater.view.base.BaseViewModel
import ru.samitin.mytranslaterr.model.datasource.DataSourceLocal

class MainViewModel(
    private val interactor:MainInteractor= MainInteractor(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal())
)):BaseViewModel<AppState>(){

    private var appState:AppState?=null

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getData(word,isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { liveDataForViewToObserve.value=AppState.Loading(null) }
                .subscribeWith(getObserver())
        )
        return super.getData(word, isOnline)
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>(){
            override fun onNext(state: AppState) {
               appState=state
                liveDataForViewToObserve.value=state
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value=AppState.Error(e)
            }

            override fun onComplete() {

            }
        }


    }

}