package com.wgw.androidflux

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mainActionCreator: MainActionCreator

    lateinit var mainStore: MainStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActionCreator = MainActionCreator()

        mainStore = MainStore()
        mainStore.register(ActionType.CHANGE_TEXT)
        mainStore.text.observe(this, Observer {
            textView.text = it
        })
    }

    fun changeText(v: View) {
        mainActionCreator.changeText()
    }
}
