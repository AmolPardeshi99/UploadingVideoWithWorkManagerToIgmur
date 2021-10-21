package com.example.uploadingvideowithworkmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class VideoWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        //displayNotification("Video Uploading is in Progress","Video Uploading")
        val videoPath: String = inputData.getString("filePath").toString()
        val apiService:ApiService = Network.getInstance().create(ApiService::class.java)
        val file = File(videoPath)
        val reqBody: RequestBody = RequestBody.create("video/*".toMediaTypeOrNull(), file)
        val multiPart: MultipartBody.Part =
            MultipartBody.Part.createFormData("video", file.name, reqBody)
        apiService.uploadVideo(multiPart,"My Video")
            .enqueue(object :Callback<ResponseData>{
                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
                    Toast.makeText(applicationContext," Video Upload Success",Toast.LENGTH_SHORT).show()
                    Log.d("Amol", "onResponse: Video Uploaded")

                }

                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Toast.makeText(applicationContext," Video Upload failed please try again",Toast.LENGTH_SHORT).show()
                    Log.d("Amol", "onResponse: Video failed")
                }
            })

        return Result.success()

    }


//    private fun displayNotification(title: String, task: String) {
//        val notificationManager =
//            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                "masai",
//                "masai",
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            notificationManager.createNotificationChannel(channel)
//        }
//        val notification: NotificationCompat.Builder =
//            NotificationCompat.Builder(applicationContext, "masai")
//                .setContentTitle(title)
//                .setContentText(task)
//                .setSmallIcon(R.mipmap.ic_launcher)
//        notificationManager.notify(1, notification.build())
//    }
}