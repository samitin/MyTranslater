package ru.samitin.mytranslaterr.model.datasource


import io.reactivex.Observable
import ru.samitin.mytranslater.model.data.DataModel
import ru.samitin.mytranslater.model.datasource.DataSource
import ru.samitin.mytranslater.model.datasource.RoomDataBaseImplementation

class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}
