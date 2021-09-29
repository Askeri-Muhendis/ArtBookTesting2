package com.ibrahimethem.artbooktesting.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

//dao muzu yazıyoruz

@Dao
interface ArtDao {

    //insert ekleme işlemi
    /** onConflict -> çakışma durumunda bizim için bir strateji belirleyecek yeniden düzenleyecek
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art : Art)

    //delete-silme işlemi
    @Delete
    suspend fun deleteArt(art : Art)

    //Query-sorgu işlemi
    @Query("SELECT * FROM arts")
    fun observeArts() : LiveData<List<Art>>

    /** LiveData asenkron çalışıyor bu yüzden suspend fun yazmamıza gerek yok burada livedata
     * yazarak gözlemlenebeilir-observed bir yapı kuruyoruz böylece değişiklikleri dinleyip gerektiğinde
     * değişiklikleri yapabiliyoruz
     *
     * */
}