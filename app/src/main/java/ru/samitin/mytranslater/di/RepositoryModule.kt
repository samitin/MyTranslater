package ru.samitin.mytranslater.di

import dagger.Module
import dagger.Provides
import ru.samitin.mytranslater.model.data.DataModel
import ru.samitin.mytranslater.model.datasource.DataSource
import ru.samitin.mytranslater.model.datasource.RetrofitImplementation
import ru.samitin.mytranslater.model.datasource.RoomDataBaseImplementation
import ru.samitin.mytranslater.model.repository.Repository
import ru.samitin.mytranslater.model.repository.RepositoryImplementation

import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(@Named(NAME_REMOTE) dataSourceRemote: DataSource<List<DataModel>>): Repository<List<DataModel>> =
        RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL) dataSourceLocal: DataSource<List<DataModel>>): Repository<List<DataModel>> =
        RepositoryImplementation(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<DataModel>> =
        RetrofitImplementation()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): DataSource<List<DataModel>> = RoomDataBaseImplementation()
}
