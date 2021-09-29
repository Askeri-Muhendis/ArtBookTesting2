package com.ibrahimethem.artbooktesting.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ibrahimethem.artbooktesting.R
import com.ibrahimethem.artbooktesting.api.RetrofitAPI
import com.ibrahimethem.artbooktesting.repo.ArtRepository
import com.ibrahimethem.artbooktesting.repo.ArtRepositoryInterface
import com.ibrahimethem.artbooktesting.roomdb.ArtDao
import com.ibrahimethem.artbooktesting.roomdb.ArtDatabase
import com.ibrahimethem.artbooktesting.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//modullümüz
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    /**retrofit-room'u build etmedik bunu yapmalıyız bunlar için dependcy injection kullanıcaz
     * tek bir sefer oluşturup her yerde kullanıcaz
     * Singleton olarak kullanmak istediğimiz için object yaptık her yerde çağırabileceğiz
     * */

    //Database oluşturduk artık istediğimiz her yerde çağırabiliriz
    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context : Context
    ) = Room.databaseBuilder(
            context,ArtDatabase::class.java,"ArtBookDB"
        ).build()

    //dao muzu oluşturacağız istediğimiz her yerde çağıracağız
    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase) = database.artDao()


    //retrofit'i oluşturduk istediğimiz yerde çalıştırıp kullanabileceğiz
    @Singleton
    @Provides
    fun injectRetrofitAPI() : RetrofitAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }

    //Glide'i oluşturuyoruz inject edebilmek için
    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(
                R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
        )

    //RepositoryMiz için ınject yazalım
    @Singleton
    @Provides
    fun injectNormalRepo(dao : ArtDao, api:RetrofitAPI)=ArtRepository(dao,api) as ArtRepositoryInterface

}