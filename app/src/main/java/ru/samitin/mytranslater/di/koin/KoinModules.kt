package ru.samitin.mytranslater.di.koin

import androidx.room.Room
import org.koin.dsl.module
import ru.samitin.history.view.history.HistoryInteractor
import ru.samitin.history.view.history.HistoryViewModel
import ru.samitin.model.data.DataModel
import ru.samitin.mytranslater.view.main.MainInteractor
import ru.samitin.mytranslater.view.main.MainViewModel
import ru.samitin.repository.*
import ru.samitin.repository.room.HistoryDataBase

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


