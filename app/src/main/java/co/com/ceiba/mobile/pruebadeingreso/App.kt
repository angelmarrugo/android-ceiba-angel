package co.com.ceiba.mobile.pruebadeingreso

import android.app.Application
import co.com.ceiba.mobile.pruebadeingreso.db.UserRoomDatabase
import co.com.ceiba.mobile.pruebadeingreso.repository.PostRepository
import co.com.ceiba.mobile.pruebadeingreso.repository.UserRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class App: Application()