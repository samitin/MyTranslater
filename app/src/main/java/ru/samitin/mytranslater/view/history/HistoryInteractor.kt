package ru.samitin.view.history

import ru.samitin.mytranslater.model.data.AppState
import ru.samitin.mytranslater.model.data.DataModel
import ru.samitin.mytranslater.model.repository.Repository
import ru.samitin.mytranslater.model.repository.RepositoryLocal
import ru.samitin.mytranslater.viewModel.Interactor


class HistoryInteractor(
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
