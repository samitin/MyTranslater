package ru.samitin.mytranslater.di

import dagger.Module
import dagger.Provides

import ru.samitin.mytranslater.interactor.MainInteractor
import ru.samitin.mytranslater.model.data.DataModel
import ru.samitin.mytranslater.model.repository.Repository
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: Repository<List<DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}
