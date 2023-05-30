package com.vm.timemanager.data

import android.content.Context
import androidx.room.*

@Database(entities = [Task::class], version = 1, exportSchema = false)

@TypeConverters(Converters::class)
abstract class DatabaseTimeManager : RoomDatabase() {

    abstract fun getDaoTimeManager() : DaoTimeManager

    companion object {
        //     @Volatile - Помечает вспомогательное поле JVM аннотированного
//     свойства как изменчивое, что означает, что записи в это поле немедленно
//     становятся видимыми для других потоков.
        /**
         * variable that points to the database
         */
        @Volatile
        private var INSTANCE: DatabaseTimeManager? = null

        /**
         * Create the database if it doesn't exist, or return the database if it exists
         */
        fun getDatabase(context: Context): DatabaseTimeManager =
            this.INSTANCE ?: synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseTimeManager::class.java,
                        "task_database"
                    ).build()

                    INSTANCE = instance
                }

                instance
            }

    }

}