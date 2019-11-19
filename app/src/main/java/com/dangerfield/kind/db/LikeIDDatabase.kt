package com.dangerfield.kind.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dangerfield.kind.model.LikeID

//@TypeConverters(Converter::class)
@Database(entities = [LikeID::class], version = 1, exportSchema = false)
abstract class LikeIDDatabase : RoomDatabase() {
    abstract fun articleDao(): LikeDAO

    companion object {
        @Volatile private var instance: LikeIDDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
                LikeIDDatabase::class.java, "likeID.db").allowMainThreadQueries()
                .build()
    }
}