package co.com.ceiba.mobile.pruebadeingreso.utils

import android.app.Application
import android.content.Context
import dagger.hilt.android.testing.HiltTestApplication
import androidx.test.runner.AndroidJUnitRunner

class CustomTestRunner: AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader,
        appName: String,
        context: Context
    ) : Application {
        return super.newApplication(
            cl, HiltTestApplication::class.java.name, context)
    }
}