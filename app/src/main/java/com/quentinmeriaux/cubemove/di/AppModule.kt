package com.quentinmeriaux.cubemove.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.quentinmeriaux.cubemove.data.AppDatabase
import com.quentinmeriaux.cubemove.data.MovementDataDao
import com.quentinmeriaux.cubemove.repository.UserDataRepository
import com.quentinmeriaux.cubemove.repository.dataStore
import com.quentinmeriaux.cubemove.ui.MainActivityViewModel
import com.quentinmeriaux.cubemove.ui.main.MainViewModel
import com.quentinmeriaux.cubemove.ui.settings.SettingsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideMovementDao(get()) }
    single { provideDataStore(androidApplication()) }

    single { UserDataRepository(get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { MainActivityViewModel(get()) }
}


fun provideDatabase(application: Application): AppDatabase {
    return Room.databaseBuilder(application, AppDatabase::class.java, "app-database")
        .fallbackToDestructiveMigration()
        .build()
}

fun provideMovementDao(database: AppDatabase): MovementDataDao {
    return database.movementDataDao()
}

fun provideDataStore(application: Application): DataStore<Preferences> {
    return application.dataStore
}