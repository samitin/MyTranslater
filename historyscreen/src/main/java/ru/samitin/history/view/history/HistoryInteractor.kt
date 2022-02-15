package ru.samitin.history.view.history

import ru.samitin.model.data.DataModel
import ru.samitin.mytranslater.model.data.AppState
import ru.samitin.mytranslater.viewModel.Interactor
import ru.samitin.repository.Repository
import ru.samitin.repository.RepositoryLocal

class
HistoryInteractor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}
