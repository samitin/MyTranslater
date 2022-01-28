package ru.samitin.mytranslater.di.koin

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.samitin.mytranslater.di.NAME_LOCAL
import ru.samitin.mytranslater.di.NAME_REMOTE
import ru.samitin.mytranslater.interactor.MainInteractor
import ru.samitin.mytranslater.model.data.DataModel
import ru.samitin.mytranslater.model.datasource.RetrofitImplementation
import ru.samitin.mytranslater.model.datasource.RoomDataBaseImplementation
import ru.samitin.mytranslater.model.repository.Repository
import ru.samitin.mytranslater.model.repository.RepositoryImplementation
import ru.samitin.mytranslater.view.viewModel.MainViewModel

val application = module {
    single <Repository<List<DataModel>>>(named(NAME_REMOTE)){
        RepositoryImplementation(RetrofitImplementation())
    }
    single <Repository<List<DataModel>>>(named(NAME_LOCAL)){
        RepositoryImplementation(RoomDataBaseImplementation())
    }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)),get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}