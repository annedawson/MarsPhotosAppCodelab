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
package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.model.MarsPhoto
// import com.example.marsphotos.network.MarsApi - now in the MarsPhotosRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import com.example.marsphotos.data.NetworkMarsPhotosRepository

/**
 * UI state for the Home screen (only 3 possible states)
 * MarsUiState.Loading, MarsUiStare.Success(String), MarsUiState.Error
 */
sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    // Represents a successful retrieval of Mars photos.
    // The original starter code stored the retrieved photos
    // as a long String of JSON key, value pairs and displayed that in the homescreen.
    // Later the code was amended in MarsViewModel.kt
    // to read the data into a list of MarsPhoto.
    // The length of the list is displayed in a string using string formatting:

    object Error : MarsUiState
    // Signifies an error during the photo fetching process.
    // Doesn't hold additional data, but its presence indicates a problem.
    object Loading : MarsUiState
    // Indicates that photos are currently being fetched.
    // Used to display loading indicators or messages to the user.
}

/*
Sealed Interface defines a limited set of possible UI states
related to fetching Mars photos.
It ensures that variables can only hold one of these specific states,
enhancing code safety and clarity.
 */

class MarsViewModel : ViewModel() {

    /** The mutable State that stores the status of the most recent request */
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].
     */
    fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = MarsUiState.Loading  // indicates that photos are being fetched
            // the loading icon will be seen on the app's screen

            marsUiState = try { // possible network access exception
                // val listResult = MarsApi.retrofitService.getPhotos() - old method

                val marsPhotosRepository = NetworkMarsPhotosRepository()
                val listResult = marsPhotosRepository.getMarsPhotos()

                // see icon in the gutter to the left of the line above
                // getMarsPhotos() is a non-blocking suspend function and
                // is only used here and is
                // defined in MarsPhotosRepository.kt

                // if all goes well and data reading is complete,
                // the following line is executed
                // otherwise an exception error is caught in a catch block
                MarsUiState.Success(  // this state is assigned to marsUiState
                    "Success: ${listResult.size} Mars photos retrieved"
                )
            } catch (e: IOException) {
                MarsUiState.Error  // if a runtime network error occurs
                                   // then this state is assigned to marsUiState
            } catch (e: HttpException) {
                MarsUiState.Error  // if a runtime HTTP error occurs e.g. page not found
                                   // then this state is assigned to marsUiState
            }
        }
    }
}
/*

viewModelScope.launch launches a new coroutine
without blocking the current thread
and returns a reference to the coroutine as a Job.
The coroutine is cancelled when the resulting job is cancelled.
Cancelled when ViewModel is cancelled.

 */