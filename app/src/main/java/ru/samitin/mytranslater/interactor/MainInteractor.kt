package ru.samitin.mytranslater.interactor

import io.reactivex.Observable
import ru.samitin.mytranslater.model.data.AppState
import ru.samitin.mytranslater.model.data.DataModel
import ru.samitin.mytranslater.model.repository.Repository

class MainInteractor (
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: Repository<List<DataModel>>
) : Interactor<AppState> {


    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            repositoryRemote.getData(word).map { AppState.Success(it) }
        } else {
            repositoryLocal.getData(word).map { AppState.Success(it) }
        }
    }
}
