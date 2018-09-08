package com.raquezha.sample

import android.app.Application
import net.raquezha.playground.Playground

open class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Playground.with(this@SampleApp, "SampleApp")
    }

}