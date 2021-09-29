package com.ibrahimethem.artbooktesting.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

//modelimizi oluşturuyoruz

@Entity(tableName = "arts")
data class Art(
    var name : String,
    var artistName : String,
    var year : Int,
    var url : String,

    //kendi kendine id'leri ayarlamasını istiyoruz
    @PrimaryKey(autoGenerate = true)
    var uuid : Int? = null
)
