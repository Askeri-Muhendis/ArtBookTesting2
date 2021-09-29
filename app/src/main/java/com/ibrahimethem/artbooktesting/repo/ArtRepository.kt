package com.ibrahimethem.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.ibrahimethem.artbooktesting.api.RetrofitAPI
import com.ibrahimethem.artbooktesting.model.ImageResponse
import com.ibrahimethem.artbooktesting.roomdb.Art
import com.ibrahimethem.artbooktesting.roomdb.ArtDao
import com.ibrahimethem.artbooktesting.util.Resource
import javax.inject.Inject

/** Modeulde oluşturduklarımız yani api/dao/room db alabilmek için injection yapıyoruz
 * */

class ArtRepository
    @Inject constructor(
        private val artDao : ArtDao,
        private val retrofitApi: RetrofitAPI
    )
    : ArtRepositoryInterface {
    override suspend fun inserArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitApi.imageSearch(imageString)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e : Exception){
            Resource.error("No Data", null)
        }
    }
}