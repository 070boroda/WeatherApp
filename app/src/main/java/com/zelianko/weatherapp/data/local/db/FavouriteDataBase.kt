package com.zelianko.weatherapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zelianko.weatherapp.data.local.model.CityDbModel

@Database(entities = [CityDbModel::class], version = 1, exportSchema = false)
abstract class FavouriteDataBase : RoomDatabase() {

    abstract fun favouriteCitiesDao(): FavouritesCitiesDao


    companion object {
        private const val DB_NAME = "FavouriteDataBase"
        private var INSTANCE: FavouriteDataBase? = null
        private val LOCK = Any()
        fun getInstance(context: Context): FavouriteDataBase {
            INSTANCE?.let { return it }

            val database = Room.databaseBuilder(
                context = context,
                klass = FavouriteDataBase::class.java,
                name = DB_NAME
            ).build()
            INSTANCE = database
            return database
        }
    }
}