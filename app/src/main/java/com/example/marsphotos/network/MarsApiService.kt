/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.marsphotos.network

import com.example.marsphotos.model.MarsPhoto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET


// The MarsApiService is used to get the data from the internet as a list of MarsPhotos.
// MarsPhoto is a data class which is serializable and hold the id and imgSrc values

 /*   this gone to AppContainer
 private const val BASE_URL =
        "https://android-kotlin-fun-mars-server.appspot.com"*/

    /**
     * Use the Retrofit builder to build a retrofit object
     * using a kotlinx.serialization converter
     */

    /* this gone to AppContainer
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()*/

    /**
     * Retrofit service object for creating api calls
     */
    interface MarsApiService {
        @GET("photos") // the GET interface adds "photos" to
        // the BASE_URL to get the absolute URL of the photos data
        // the function just returns the list of photos
        suspend fun getPhotos(): List<MarsPhoto> // originally data type was String
        // and displayed all the photos in that one string on the HomeScreen.
        // Later in the code, the MarsPhoto data class is created as a place to store the
        // serialized data coming back from the MarsApiService

        // The MarsViewModel calls getPhotos() which returns a list of MarsPhotos

    }



    /**
     * A public Api object that exposes the lazy-initialized Retrofit service
     */
    //object MarsApi {
        /*
        The call to create() function on a Retrofit object is expensive
        in terms of memory, speed, and performance. The app needs only
        one instance of the Retrofit API service, so you expose the service
        to the rest of the app using object declaration.
         */

        /* this gone to AppContainer
        val retrofitService: MarsApiService by lazy {
            retrofit.create(MarsApiService::class.java)
        }*/

        // Note: Remember "lazy initialization" is when object creation is purposely delayed,
        // until you actually need that object, to avoid unnecessary computation
        // or use of other computing resources.
        // Kotlin has first-class support for lazy instantiation.
// }
