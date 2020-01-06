package com.kar.enes.app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kar.enes.app.di.annotations.ViewModelKey
import com.kar.enes.app.ui.splash.SplashViewModel
import com.kar.enes.app.ui.base.ViewModelFactory
import com.kar.enes.app.ui.news.NewsViewModel
import com.kar.enes.app.ui.sources.SourcesActivity
import com.kar.enes.app.ui.sources.SourcesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

/**
 * Created by M.Enes on 24.04.2019
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SourcesViewModel::class)
    abstract fun bindSourcesViewModel(sourcesViewModel: SourcesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    abstract fun bindNewsViewModel(newsViewModel: NewsViewModel): ViewModel

    @Binds
    @Singleton
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}