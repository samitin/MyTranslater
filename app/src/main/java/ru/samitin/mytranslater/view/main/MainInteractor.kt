package ru.samitin.mytranslater.view.main

import ru.samitin.model.dataDto.SearchResultDto
import ru.samitin.mytranslater.model.data.AppState
import ru.samitin.mytranslater.utils.mapSearchResultToResult

import ru.samitin.mytranslater.viewModel.Interactor
import ru.samitin.repository.Repository
import ru.samitin.repository.RepositoryLocal

class MainInteractor(
    private val repositoryRemote: Repository<List<SearchResultDto>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResultDto>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(mapSearchResultToResult(repositoryRemote.getData(word)))
            repositoryLocal.saveToDB(appState)
        } else {
            appState = AppState.Success(mapSearchResultToResult(repositoryLocal.getData(word)))
        }
        return appState
    }
}
