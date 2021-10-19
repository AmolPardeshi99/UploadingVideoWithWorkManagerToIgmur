package com.example.uploadingvideowithworkmanager


import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("success")
    val success: Boolean?
)