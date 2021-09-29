package com.ibrahimethem.artbooktesting.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibrahimethem.artbooktesting.model.ImageResponse
import com.ibrahimethem.artbooktesting.repo.ArtRepositoryInterface
import com.ibrahimethem.artbooktesting.roomdb.Art
import com.ibrahimethem.artbooktesting.util.Resource
import java.lang.Exception
import kotlinx.coroutines.launch

//tüm view işlemlerimizi tek bir viewmodel içinde yapıcaz

/** Inject yapmak için ViewModel'da @ViewModelInject'i kullanıyoruz
 *
 * */
class ArtViewModel @ViewModelInject constructor(
    private val repository: ArtRepositoryInterface
) : ViewModel() {
    //Art fragment için livedata
    val artList = repository.getArt()

    //ImageApı fragment
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage

    //artdetails

    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage : LiveData<Resource<Art>>
        get() = insertArtMsg

    fun resetInsertArtMsg(){
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    //fonksiyonlarımız
    fun setSelectedImage(url : String){
        selectedImage.postValue(url)
    }

    fun deleteArt(art: Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }
    

    fun insertArt(art: Art) = viewModelScope.launch {
        repository.inserArt(art)
    }
    /*
    fun insertArt(art : Art) = viewModelScope.launch{
        
    }*/

    //detailArt'a girilen değerleri kontrol ediyoruz
    fun makeArt(name : String,artistName : String,year : String){
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()){
            insertArtMsg.postValue(Resource.error("Enter name,string,year",null))
            return //github
        }

        val yearInt = try {
            year.toInt()
        }catch (e : Exception){
            insertArtMsg.postValue(Resource.error("Year Should be number",null))
            return
        }

        val art = Art(name,artistName,yearInt,selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }

    fun searchForImage(searchString : String){
        if (searchString.isEmpty()){
            return
        }

        images.value = Resource.loading(null)

        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }
    }
}
