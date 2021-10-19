package com.example.uploadingvideowithworkmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class VideoWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
            displayNotification("Video Uploading is in Progress","Video Uploading")
            callAPI()
        return Result.success()
    }


     fun callAPI() {
        val videoPath = inputData.getString("filePath")
        val apiService = Network.getInstance().create(ApiService::class.java)
        val file = File(videoPath)
        val reqBody: RequestBody = RequestBody.create("video/*".toMediaTypeOrNull(), file)
        val multiPart = MultipartBody.Part.createFormData("video", file.name, reqBody)

         CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.uploadVideo(multiPart, "amol")

             response?.let {
                 if (it.success == true){
                     Log.d("TAG", "callAPI: upload completed ")
                 }
             }
        }


    }


    private fun displayNotification(title: String, task: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "masai",
                "masai",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        val notification: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, "masai")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.mipmap.ic_launcher)
        notificationManager.notify(1, notification.build())
    }
}