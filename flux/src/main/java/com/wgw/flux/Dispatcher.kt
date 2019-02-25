package com.wgw.flux

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Action分发器
 *
 * @author Created by 汪高皖 on 2019/2/21 0021 14:23
 */
object Dispatcher {
    private val mRxBus: RxBus = RxBus()

    internal fun register(store: Store, vararg actionTypes: String) {
        FluxLogger.logRegisterStore(store.javaClass.simpleName, *actionTypes)
        store.disposable = mRxBus.toObservable()
            .filter { action ->
                if (action.target != null) {
                    return@filter action.target == store
                } else if (actionTypes.isEmpty()) {
                    return@filter true
                }
                return@filter actionTypes.any { it == action.type }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ next ->
                store.handlerAction(next)
            }, { throwable ->
                FluxLogger.logHandleException(
                    store.javaClass.simpleName,
                    throwable
                )
            })
    }

    /**
     * 分发[Action]
     */
    @JvmStatic
    fun postAction(action: Action) {
        FluxLogger.logPostAction(action)
        mRxBus.post(action)
    }

    /**
     * 通过[actionType]或[target]指定分发目标且[target]用于唯一指定一个[Store]接收当前Action，
     * 可携带[data]和[throwable]]数据
     */
    @JvmOverloads
    @JvmStatic
    fun postAction(
        actionType: String,
        data: Any? = null,
        throwable: Throwable? = null,
        target: Store? = null
    ) {
        val action = Action.getInstance(actionType)
        action.target = target
        action.data = data
        action.throwable = throwable
        Dispatcher.postAction(action)
    }
}
