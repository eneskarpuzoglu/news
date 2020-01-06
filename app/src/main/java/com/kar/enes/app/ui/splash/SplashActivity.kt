package com.kar.enes.app.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kar.enes.app.BR
import com.kar.enes.app.R
import com.kar.enes.app.databinding.ActivitySplashBinding
import com.kar.enes.app.ui.base.BaseActivity
import com.kar.enes.app.ui.sources.SourcesActivity
import javax.inject.Inject


/**
 * Created by M.Enes on 5/9/2019
 */
class SplashActivity  : BaseActivity<ActivitySplashBinding, SplashViewModel>(),SplashNavigator {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var splashViewModel: SplashViewModel

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): SplashViewModel {
        splashViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java)
        return splashViewModel
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashViewModel.setNavigator(this)
        splashViewModel.start()

    }

    override fun openSourcesActivity(){
        val intent = Intent(this, SourcesActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        finish()
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}
