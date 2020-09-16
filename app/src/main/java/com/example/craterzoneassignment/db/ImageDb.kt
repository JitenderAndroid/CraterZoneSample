package com.example.craterzoneassignment.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RepoSearchResult::class], version = 1, exportSchema = false)
public abstract class ImageDb : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ImageDb? = null

        fun getDatabase(context: Context): ImageDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ImageDb::class.java,
                    "image_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}