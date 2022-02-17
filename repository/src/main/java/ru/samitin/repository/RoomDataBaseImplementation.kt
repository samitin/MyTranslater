package ru.samitin.repository

import ru.samitin.mytranslater.model.data.AppState
import ru.samitin.model.dataDto.SearchResultDto
import ru.samitin.repository.room.HistoryDao


class RoomDataBaseImplementation(private val historyDao: HistoryDao) : DataSourceLocal<List<SearchResultDto>> {

    override suspend fun getData(word: String): List<SearchResultDto> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}
