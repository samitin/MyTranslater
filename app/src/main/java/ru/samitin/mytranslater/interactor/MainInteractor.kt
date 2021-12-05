package ru.samitin.mytranslater.interactor



import io.reactivex.Observable
import ru.samitin.mytranslater.di.NAME_LOCAL
import ru.samitin.mytranslater.di.NAME_REMOTE
import ru.samitin.mytranslater.model.data.AppState
import ru.samitin.mytranslater.model.data.DataModel
import ru.samitin.mytranslater.model.repository.Repository
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    @Named(NAME_REMOTE) val repositoryRemote: Repository<List<DataModel>>,
    @Named(NAME_LOCAL) val repositoryLocal: Repository<List<DataModel>>
) : Interactor<AppState> {


    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            repositoryRemote.getData(word).map { AppState.Success(it) }
        } else {
            repositoryLocal.getData(word).map { AppState.Success(it) }
        }
    }
}
