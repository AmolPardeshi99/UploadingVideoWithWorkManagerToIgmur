package com.example.uploadingvideowithworkmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class VideoWorker(context: Context,workerParameters: WorkerParameters):Worker(context,workerParameters) {

    override fun doWork(): Result {
        for (i in 1..10){
            Thread.sleep(500)
            Log.d("TAG", "doWork: $i")
        }
        return Result.success()

    }


    fun callAPI(){

    }
}