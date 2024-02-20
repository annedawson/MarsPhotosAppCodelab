package com.example.marsphotos.data

import com.example.marsphotos.model.MarsPhoto
// import com.example.marsphotos.network.MarsApi - no longer needed
import com.example.marsphotos.network.MarsApiService

interface MarsPhotosRepository {
    suspend fun getMarsPhotos(): List<MarsPhoto> // abstract function not implemented
}

class NetworkMarsPhotosRepository(
    private val marsApiService: MarsApiService
) : MarsPhotosRepository {
    // this class implements this interface
   /* old method
   override suspend fun getMarsPhotos(): List<MarsPhoto> {
        return MarsApi.retrofitService.getPhotos()
    }*/

    override suspend fun getMarsPhotos(): List<MarsPhoto> = marsApiService.getPhotos()
}
