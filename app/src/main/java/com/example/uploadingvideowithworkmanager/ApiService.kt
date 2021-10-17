package com.example.uploadingvideowithworkmanager

import okhttp3.MultipartBody
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Headers("Authorization: Client-ID 322c898bb8cc046")
    @POST("3/upload")
    @Multipart
    suspend fun uploadVideo(
        @Part file: MultipartBody.Part,
        @Part("title") title:String
    )
}