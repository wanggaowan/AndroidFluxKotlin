package com.wgw.androidflux

import com.wgw.flux.Dispatcher
import java.util.*

/**
 * @author Created by 汪高皖 on 2019/2/25 0025 09:28
 */
class MainActionCreator {
    fun changeText() {
        val random = Random()
        val text = "random value is " + random.nextInt(100000)
        Dispatcher.postAction(ActionType.CHANGE_TEXT, text)
    }
}
