package com.example.uploadingvideowithworkmanager


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("account_id")
    val accountId: Any?,
    @SerializedName("account_url")
    val accountUrl: Any?,
    @SerializedName("ad_type")
    val adType: Any?,
    @SerializedName("ad_url")
    val adUrl: Any?,
    @SerializedName("animated")
    val animated: Boolean?,
    @SerializedName("bandwidth")
    val bandwidth: Int?,
    @SerializedName("datetime")
    val datetime: Int?,
    @SerializedName("deletehash")
    val deletehash: String?,
    @SerializedName("description")
    val description: Any?,
    @SerializedName("favorite")
    val favorite: Boolean?,
    @SerializedName("has_sound")
    val hasSound: Boolean?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("hls")
    val hls: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("in_gallery")
    val inGallery: Boolean?,
    @SerializedName("in_most_viral")
    val inMostViral: Boolean?,
    @SerializedName("is_ad")
    val isAd: Boolean?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("mp4")
    val mp4: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("nsfw")
    val nsfw: Any?,
    @SerializedName("section")
    val section: Any?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("tags")
    val tags: List<Any>?,
    @SerializedName("title")
    val title: Any?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("views")
    val views: Int?,
    @SerializedName("vote")
    val vote: Any?,
    @SerializedName("width")
    val width: Int?
)