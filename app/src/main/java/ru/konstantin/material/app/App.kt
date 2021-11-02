package ru.konstantin.material.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import ru.konstantin.beautybox.room.ItemDataBase
import ru.konstantin.material.room.ItemDao
import java.lang.IllegalStateException

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        context = applicationContext

    }

    companion object {
        private var appInstance: App? = null
        private var db: ItemDataBase? = null
        private const val DB_NAME = "SpaceBase.db"

        lateinit var context: Context

        fun getItemDao(): ItemDao {
            if (db == null) {
                synchronized(ItemDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) {
                            throw IllegalStateException("Application ids null meanwhile creating database")
                        }
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            ItemDataBase::class.java,
                            DB_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return db!!.historyDao()
        }
    }
}

interface IContextProvider{
    val context: Context
}

object ContextProvider: IContextProvider {
    override val context: Context
        get() = App.context
}