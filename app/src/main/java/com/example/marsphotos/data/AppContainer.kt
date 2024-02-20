package com.example.marsphotos.data

import retrofit2.Retrofit
import com.example.marsphotos.network.MarsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val marsPhotosRepository: MarsPhotosRepository
}

class DefaultAppContainer : AppContainer {

    private val baseUrl =
        "https://android-kotlin-fun-mars-server.appspot.com"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }

    // Note: Remember "lazy initialization" is when object creation is purposely delayed,
    // until you actually need that object, to avoid unnecessary computation
    // or use of other computing resources.
    // Kotlin has first-class support for lazy instantiation.

    /*
            The call to create() function on a Retrofit object is expensive
            in terms of memory, speed, and performance. The app needs only
            one instance of the Retrofit API service, so you expose the service
            to the rest of the app using object declaration.
     */

    override val marsPhotosRepository: MarsPhotosRepository by lazy {
        NetworkMarsPhotosRepository(retrofitService)
    }
}

