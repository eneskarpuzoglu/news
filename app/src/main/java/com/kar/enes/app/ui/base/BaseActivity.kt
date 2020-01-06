package com.kar.enes.app.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.kar.enes.app.R
import dagger.android.AndroidInjection
import karpuzoglu.enes.com.fastdialog.FastDialog
import karpuzoglu.enes.com.fastdialog.FastDialogBuilder
import karpuzoglu.enes.com.fastdialog.Type

/**
 * Created by M.Enes on 24.04.2019
 */
abstract class BaseActivity<T: ViewDataBinding,V: BaseViewModel<*>> : AppCompatActivity() {
    private var mViewDataBinding: T? = null
    private var mViewModel: V? = null
    private lateinit var pDialog: FastDialog

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        performDataBinding()
        pDialog = FastDialogBuilder(this, Type.PROGRESS)
            .progressText(getString(R.string.please_wait))
            .setFullScreen(true)
            .cancelable(false)
            .create()

    }

    fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel
        mViewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding?.executePendingBindings()
    }

    protected fun showProgress(){
        pDialog.show()
    }

    protected fun dismissProgress(){
        pDialog.dismiss()
    }
}