package ru.samitin.mytranslater.di.koin

import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.samitin.history.view.history.HistoryActivity
import ru.samitin.history.view.history.HistoryInteractor
import ru.samitin.history.view.history.HistoryViewModel
import ru.samitin.model.dataDto.SearchResultDto
import ru.samitin.mytranslater.view.main.MainActivity
import ru.samitin.mytranslater.view.main.MainInteractor
import ru.samitin.mytranslater.view.main.MainViewModel
import ru.samitin.repository.*
import ru.samitin.repository.room.HistoryDataBase

val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<Repository<List<SearchResultDto>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<RepositoryLocal<List<SearchResultDto>>> { RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
}

val mainScreen = module {
    scope(named<MainActivity>()) {
        scoped { MainInteractor(get(), get()) }
        viewModel { MainViewModel(get()) }
    }

}

val historyScreen = module {
    scope(named<HistoryActivity>()) {
        scoped{ HistoryInteractor(get(), get()) }
        viewModel { HistoryViewModel(get()) }
    }


}


