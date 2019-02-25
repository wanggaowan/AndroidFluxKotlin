package com.wgw.flux


import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * 发送Action，增加订阅
 *
 * @author Created by 汪高皖 on 2019/2/21 0021 13:52
 */
internal class RxBus {
    private val bus = PublishSubject.create<Any>().toSerialized()

    /**
     * 发射[Action]
     */
    fun post(action: Action) {
        bus.onNext(action)
    }

    /**
     * return an [Observable] that emits items from the source ObservableSource of type [Action].class
     */
    fun toObservable(): Observable<Action> {
        return bus.ofType(Action::class.java)
    }
}
