package com.wgw.flux


import android.util.Log

/**
 * Flux日志工具
 *
 * @author Created by 汪高皖 on 2019/2/21 0021 14:00
 */
internal object FluxLogger {
    private const val TAG = "RxFlux"
    private var logEnabled = BuildConfig.DEBUG

    /**
     * 打印[Store]注册消息
     */
    fun logRegisterStore(storeName: String, vararg actionType: String) {
        if (logEnabled) {
            if (actionType.isEmpty()) {
                Log.d(TAG, "Store $storeName has registered all action")
            } else {
                Log.d(TAG, "Store $storeName has registered action : $actionType")
            }
        }
    }

    /**
     * 打印[Store]取消注册的消息
     */
    fun logUnregisterStore(storeName: String) {
        if (logEnabled) {
            Log.d(TAG, "Store $storeName has unregistered")
        }
    }

    /**
     * 打印[Action]分发消息
     */
    fun logPostAction(action: Action) {
        if (logEnabled) {
            Log.d(TAG, "Dispatcher post action : $action")
        }
    }

    /**
     * 打印异常消息
     */
    fun logHandleException(storeName: String, e: Throwable) {
        if (logEnabled) {
            Log.e(TAG, "Store $storeName handle action throws Exceptiop", e)
        }
    }
}
