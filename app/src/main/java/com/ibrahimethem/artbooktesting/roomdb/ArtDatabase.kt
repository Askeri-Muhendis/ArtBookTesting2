package com.ibrahimethem.artbooktesting.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
/*
//database oluşturuyoruz entities kısmına @Entity anatasyonunu koydugumuz modeli ekliyoruz
@Database(entities = [Art::class],version = 1)
abstract class ArtDatabase : RoomDatabase() {

    abstract fun artDao() : ArtDao

}*/

@Database(entities = [Art::class],version = 1)
abstract class ArtDatabase : RoomDatabase() {
    abstract fun artDao() : ArtDao
}