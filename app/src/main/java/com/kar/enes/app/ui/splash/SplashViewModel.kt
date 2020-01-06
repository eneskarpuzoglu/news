package com.kar.enes.app.ui.splash

import com.kar.enes.app.data.DataManager
import com.kar.enes.app.ui.base.BaseViewModel
import io.reactivex.Completable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Created by M.Enes on 5/9/2019
 */
class SplashViewModel @Inject constructor(dataManager: DataManager): BaseViewModel<SplashNavigator>(dataManager) {

    fun start(){
        // 2 sn sonra SourcesActivity'i acar
        Completable.complete()
            .delay(2, TimeUnit.SECONDS)
            .doOnComplete { getNavigator()?.openSourcesActivity() }
            .subscribe()
    }
}