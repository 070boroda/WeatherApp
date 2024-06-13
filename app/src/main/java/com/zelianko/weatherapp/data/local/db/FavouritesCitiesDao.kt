package com.zelianko.weatherapp.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zelianko.weatherapp.data.local.model.CityDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesCitiesDao {

    @Query("SELECT * FROM favourite_cities")
    fun getFavouriteCities(): Flow<List<CityDbModel>>

    @Query("SELECT EXISTS (SELECT * FROM favourite_cities WHERE id = :cityId LIMIT 1)")
    fun observeIsFavourite(cityId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavourite(cityDbModel: CityDbModel)

    @Query("DELETE FROM favourite_cities WHERE id = :cityId")
    fun deleteFromFavourite(cityId: Int)
}