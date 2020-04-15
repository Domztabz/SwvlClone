package com.andromeda.swvlclone.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.andromeda.swvlclone.DeliveryLocation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf( Place::class, DeliveryLocation::class), version = 1, exportSchema = false)
public abstract class ImmicartRoomDatabase : RoomDatabase() {


    abstract fun placeDao(): DeliveryDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ImmicartRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ImmicartRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ImmicartRoomDatabase::class.java,
                    "word_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}