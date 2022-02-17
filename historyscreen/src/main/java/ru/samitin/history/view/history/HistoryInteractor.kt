package ru.samitin.history.view.history

import ru.samitin.model.dataDto.SearchResultDto
import ru.samitin.mytranslater.model.data.AppState
import ru.samitin.mytranslater.utils.mapSearchResultToResult
import ru.samitin.mytranslater.viewModel.Interactor
import ru.samitin.repository.Repository
import ru.samitin.repository.RepositoryLocal

class
HistoryInteractor(
    private val repositoryRemote: Repository<List<SearchResultDto>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResultDto>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            mapSearchResultToResult(
                if (fromRemoteSource) {
                    repositoryRemote
                } else {
                    repositoryLocal
                }.getData(word)
            )

        )
    }
}
