package ru.samitin.mytranslater.di.koin

import androidx.room.Room
import geekbrains.ru.translator.room.HistoryDataBase

import org.koin.dsl.module
import ru.samitin.mytranslater.view.main.MainInteractor
import ru.samitin.mytranslater.model.data.DataModel
import ru.samitin.mytranslater.model.datasource.RetrofitImplementation
import ru.samitin.mytranslater.model.datasource.RoomDataBaseImplementation
import ru.samitin.mytranslater.model.repository.Repository
import ru.samitin.mytranslater.model.repository.RepositoryImplementation
import ru.samitin.mytranslater.model.repository.RepositoryImplementationLocal
import ru.samitin.mytranslater.model.repository.RepositoryLocal
import ru.samitin.mytranslater.view.main.MainViewModel
import ru.samitin.view.history.HistoryInteractor
import ru.samitin.view.history.HistoryViewModel

val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<Repository<List<DataModel>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<RepositoryLocal<List<DataModel>>> { RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
}

val mainScreen = module {
    factory { MainViewModel(get()) }
    factory { MainInteractor(get(), get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}


