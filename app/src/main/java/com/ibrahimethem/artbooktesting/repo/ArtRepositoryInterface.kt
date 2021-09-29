package com.ibrahimethem.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.ibrahimethem.artbooktesting.model.ImageResponse
import com.ibrahimethem.artbooktesting.roomdb.Art
import com.ibrahimethem.artbooktesting.util.Resource

/** test yapabilmek için bir fake repo oluşturacağız bu yüzden interface oluşturuyoruz
 *  burada yazdıklarımızı hem asıl repomuzda hemde fake repomuzda çağırarak kullanabileceğiz
 *
 *  test ortamlarının stabil olması gerektiği için fake repo oluşturuyoruz böylece başarılı veya
 *  başarısız olduğumuz kesin şekilde dönücek. Testlerde network-thread işlemleri olmamalı
 * */
interface ArtRepositoryInterface {
    suspend fun inserArt(art : Art)

    suspend fun deleteArt(art : Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString : String) : Resource<ImageResponse>

}