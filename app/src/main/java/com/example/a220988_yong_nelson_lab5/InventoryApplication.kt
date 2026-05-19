package com.example.a220988_yong_nelson_lab5

import android.app.Application
import com.example.a220988_yong_nelson_lab5.data.AppContainer
import com.example.a220988_yong_nelson_lab5.data.AppDataContainer

class InventoryApplication : Application() {
    /**
     * AppContainer instance used by the rest of the classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}