package ru.samitin.mytranslater.view.main

import ru.samitin.model.data.DataModel
import ru.samitin.mytranslater.model.data.AppState

import ru.samitin.mytranslater.viewModel.Interactor
import ru.samitin.repository.Repository
import ru.samitin.repository.RepositoryLocal

class MainInteractor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(repositoryRemote.getData(word))
            repositoryLocal.saveToDB(appState)
        } else {
            appState = AppState.Success(repositoryLocal.getData(word))
        }
        return appState
    }
}
