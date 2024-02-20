package com.example.marsphotos.fake

import com.example.marsphotos.data.NetworkMarsPhotosRepository
import com.example.marsphotos.model.MarsPhoto
import org.junit.Test
import org.junit.Assert.assertEquals



class NetworkMarsRepositoryTest {

    @Test
    fun networkMarsPhotosRepository_getMarsPhotos_verifyPhotoList(){
        val repository = NetworkMarsPhotosRepository(
            marsApiService = FakeMarsApiService()
        )
    }
}