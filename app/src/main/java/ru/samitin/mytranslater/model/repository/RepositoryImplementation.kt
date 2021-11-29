package ru.samitin.mytranslater.model.repository


import io.reactivex.Observable
import ru.samitin.mytranslater.model.data.DataModel
import ru.samitin.mytranslater.model.datasource.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }
}
