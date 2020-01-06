package com.kar.enes.app.di

import com.kar.enes.app.ui.news.NewsActivity
import com.kar.enes.app.ui.news.NewsActivityModule
import com.kar.enes.app.ui.sources.SourcesActivity
import com.kar.enes.app.ui.sources.SourcesActivityModule
import com.kar.enes.app.ui.splash.SplashActivity
import com.kar.enes.app.ui.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by M.Enes on 5/8/2019
 */
@Module
abstract class BuilderModule {

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    internal abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [SourcesActivityModule::class])
    internal abstract fun bindSourcesActivity(): SourcesActivity

    @ContributesAndroidInjector(modules = [NewsActivityModule::class])
    internal abstract fun bindNewsActivity(): NewsActivity
}