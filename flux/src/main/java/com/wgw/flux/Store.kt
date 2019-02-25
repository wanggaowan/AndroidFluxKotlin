package com.wgw.flux

import android.arch.lifecycle.ViewModel

import io.reactivex.disposables.Disposable

/**
 * 存储并处理Action数据并向外部通知处理结果
 *
 * @author Created by 汪高皖 on 2019/2/21 0021 14:13
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class Store : ViewModel() {
    internal var disposable: Disposable? = null
        set(value) {
            if (field?.isDisposed == false) {
                field?.dispose()
            }
            field = value
        }

    /**
     * return 当前是否注册了[Action]监听
     */
    val isRegistered: Boolean = disposable != null

    /**
     * 处理[Action]数据，使用完请调用[Action.recycleInstance]进行回收处理
     *
     * @param action 当前[Store]注册的[Action]
     */
    protected abstract fun onAction(action: Action)

    override fun onCleared() {
        unRegister()
    }

    /**
     * 供[Dispatcher]调用
     */
    internal fun handlerAction(action: Action) {
        onAction(action)
    }

    /**
     * 根据[actionType]注册[Action]以监听[Dispatcher]分发的[Action]
     */
    fun register(vararg actionType: String) {
        Dispatcher.register(this, *actionType)
    }

    /**
     * 取消注册
     */
    fun unRegister() {
        FluxLogger.logUnregisterStore(javaClass.simpleName)
        if (this.disposable?.isDisposed == false) {
            this.disposable?.dispose()
        }
        this.disposable = null
    }
}
