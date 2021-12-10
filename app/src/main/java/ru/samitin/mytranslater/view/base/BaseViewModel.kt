package ru.samitin.mytranslater.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.samitin.mytranslater.model.data.AppState
import ru.samitin.mytranslater.rx.SchedulerProvider

abstract class BaseViewModel<T: AppState>(
    protected val liveDataForViewToObserve:MutableLiveData<T> =MutableLiveData(),
    protected val compositeDisposable: CompositeDisposable= CompositeDisposable(),
    protected val schedulerProvider:SchedulerProvider= SchedulerProvider()
) : ViewModel(){

    abstract fun getData(word:String,isOnline:Boolean)//:LiveData<T> =liveDataForViewToObserve

    override fun onCleared() {
        compositeDisposable.clear()
    }
}